package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.mappers.InvoiceMapper;
import solvas.service.models.*;

import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * InvoiceService class
 */
@Service
public class InvoiceService extends AbstractService<Invoice, ApiInvoice> {

    // Helper for calculating premiums
    private final PremiumCalculator premiumCalc;
    //Dao context
    protected DaoContext context;

    /**
     * Construct an abstract service
     *
     * @param context the DAO context
     * @param mapper  the mapper between the api model and the model
     */
    @Autowired
    public InvoiceService(DaoContext context, InvoiceMapper mapper) {
        super(context.getInvoiceDao(), mapper);
        this.context = context;
        this.premiumCalc = new PremiumCalculator();
    }


    /**
     * Finds all types of insurance in the database
     *
     * @return types of insurance
     */
    public Collection<String> findAllInsuranceTypes() {
        return context.getInsuranceTypeDao().findAll().stream().map(InsuranceType::getName).collect(Collectors.toSet());

    }

    public ApiInvoice findActiveInvoiceByType(int fleetId, InvoiceType type) throws EntityNotFoundException {
        // fleet is given as parameter
        // Read fleet from database
        Fleet fleet = context.getFleetDao().find(fleetId);
        // if the fleet is not found, it will throw a EntityNotFoundException, which will be caught by
        //the method not found in AbstractController
        generateMissingInvoices(fleet);
        return mapper.convertToApiModel(generateNextBillingInvoice(fleet));

    }

    /**
     * Generate invoice with type billing. If the new invoice period has not yet been completed
     * then the new invoice will not be saved. With other words, if the next invoice is equal to the current
     * running invoice, then nothing will be saved.
     * A period is considered not completed if the enddate is before <u>or equal</u> to now.
     *
     * @param fleet the fleet
     * @return newly generated invoice
     */
    private Invoice generateNextBillingInvoice(Fleet fleet) {
        LocalDateTime startDate = getStartDateNextInvoice(fleet);
        LocalDateTime endDate = startDate.plusMonths(fleet.getFacturationPeriod());
        Invoice invoice = new Invoice();
        invoice.setStartDate(startDate);
        invoice.setEndDate(endDate);
        invoice.setPaid(false);
        invoice.setFleet(fleet);
        invoice.setType(InvoiceType.BILLING);
        invoice.setAmount(premiumCalc.calculateTotalAmount(fleet, startDate));
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());
        if (endDate.isBefore(LocalDateTime.now())) {
            context.getInvoiceDao().save(invoice);
        }
        return invoice;
    }

    /**
     * Generates the invoices for each past period which does not have one yet.
     * Each one will be saved except for the current one.
     *
     * @param fleet Fleet for which the invoices have to be calculated
     * @return amount of invoices generated and saved
     */
    public int generateMissingInvoices(Fleet fleet) {
        Invoice lastGenerated = null;
        int count = 0;
        do {
            lastGenerated = generateNextBillingInvoice(fleet);
            count++;
        } while (!lastGenerated.getEndDate().isAfter(LocalDateTime.now()));
        return count - 1; // current invoice is not saved. See method generateNextBillingInvoice.
    }

    /**
     * Get the startdate of the next invoice for this fleet.
     * If this fleet never had an invoice yet, the creation date of the fleet is chosen
     *
     * @param fleet fleet
     * @return the start date
     */
    public LocalDateTime getStartDateNextInvoice(Fleet fleet) {
        return context.getInvoiceDao().latestEndDateByFleet(fleet);
    }


    private class PremiumCalculator {

        /**
         * Calculate premium of a vehicle subscription from a given date.
         * All contracts linked to the subscription which are still active after the given date
         * will be included.
         *
         * @param f             the fleet subscription
         * @param startDateTime start date from where contracts have to be included
         * @param endDateTime   start date to where contracts have to be included
         * @return calculated premium
         */
        BigDecimal calculatePremium(FleetSubscription f, LocalDateTime startDateTime, LocalDateTime endDateTime) {
            LocalDate startDate = startDateTime.toLocalDate(), endDate = endDateTime.toLocalDate();
            BigDecimal totalAmount = BigDecimal.ZERO;
            Collection<Contract> contracts = context.getContractDao()
                    .findByFleetSubscriptionAndOverlaps(f, startDateTime, endDateTime);
            for (Contract contract : contracts) {
                BigDecimal premium = BigDecimal.valueOf(contract.getPremium());
                if (contract.getStartDate().toLocalDate().isAfter(startDate) || contract.getEndDate().toLocalDate().isBefore(endDate)) {
                    LocalDate maxStart = contract.getStartDate().toLocalDate().isAfter(startDate) ?
                            contract.getStartDate().toLocalDate() : startDate;
                    LocalDate minEnd = contract.getEndDate().toLocalDate().isAfter(endDate) ?
                            endDate:contract.getEndDate().toLocalDate();
                    premium = premium.multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(maxStart, minEnd)))
                            .divide(BigDecimal.valueOf(ChronoUnit.DAYS.between(startDate, endDate)), RoundingMode.HALF_UP);
                    //Todo test divide by zeo?

                }
                Tax tax = context.getTaxDao().findDistinctByVehicleTypeNameAndInsuranceTypeName(
                        f.getVehicle().getType().getName(), contract.getInsuranceType().getName());
                totalAmount = totalAmount.add((tax.getTax().add(BigDecimal.ONE)).multiply(premium));
            }
            return totalAmount;
        }

        /**
         * Finds all fleet subscriptions that lay in between startdate and (startdate + facturation period)
         * calls the method calculatePremium for each subscription
         *
         * @param fleet
         * @param startDate
         * @return
         */
        BigDecimal calculateTotalAmount(Fleet fleet, LocalDateTime startDate) {
            LocalDateTime endDate = startDate.plusMonths(fleet.getFacturationPeriod());
            return context.getFleetSubscriptionDao()
                    .findByFleetAndStartDateAndEndDate(fleet, startDate.toLocalDate(), endDate.toLocalDate()).stream()
                    .map(fleetSubscription -> calculatePremium(fleetSubscription, startDate, endDate))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }
}