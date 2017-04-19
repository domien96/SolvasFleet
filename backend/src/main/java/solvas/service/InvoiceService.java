package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.InsuranceTypeDao;
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
public class InvoiceService extends AbstractService<Invoice,ApiInvoice> {

    // Helper for calculating premiums
    private final PremiumCalculator premiumCalc;
    //Dao context
    protected DaoContext context;

    /**
     * Construct an abstract service
     *
     * @param context the DAO context
     * @param mapper   the mapper between the api model and the model
     */
    @Autowired
    public InvoiceService(DaoContext context, InvoiceMapper mapper) {
        super(context.getInvoiceDao(), mapper);
        this.context=context;
        this.premiumCalc = new PremiumCalculator();
    }


    /**
     * Finds all types of insurance in the database
     * @return types of insurance
     */
    public Collection<String> findAllInsuranceTypes() {
        return context.getInsuranceTypeDao().findAll().stream().map(InsuranceType::getName).collect(Collectors.toSet());

    }

    public ApiInvoice findActiveInvoiceByType(int fleetId,InvoiceType type) throws EntityNotFoundException{
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
     * @param fleet the fleet
     * @return newly generated invoice
     */
    private Invoice generateNextBillingInvoice(Fleet fleet) {
        LocalDateTime startDate = getStartDateNextInvoice(fleet),
        endDate = startDate.plusMonths(fleet.getFacturationPeriod());
        Invoice invoice = new Invoice();
        invoice.setStartDate(startDate);
        invoice.setEndDate(endDate);
        invoice.setPaid(false);
        invoice.setFleet(fleet);
        invoice.setType(InvoiceType.BILLING);
        invoice.setAmount(premiumCalc.calculateTotalAmount(fleet,startDate));
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());
        if(endDate.isBefore(LocalDateTime.now())) {
            context.getInvoiceDao().save(invoice);
        }
        return invoice;
    }

    /**
     * Generates the invoices for each past period which does not have one yet.
     * Each one will be saved except for the current one.
     * @param fleet Fleet for which the invoices have to be calculated
     * @return amount of invoices generated and saved
     */
    public int generateMissingInvoices(Fleet fleet) {
        Invoice lastGenerated = null;
        int count = 0;
        do {
          lastGenerated = generateNextBillingInvoice(fleet);
          count++;
        } while( !lastGenerated.getEndDate().isAfter(LocalDateTime.now()));
        return count-1; // current invoice is not saved. See method generateNextBillingInvoice.
    }

    /**
     * Get the startdate of the next invoice for this fleet.
     * If this fleet never had an invoice yet, the startdate of the
     * active fleetsubscription will be taken.
     * @param fleet fleet
     * @return the start date
     */
    public LocalDateTime getStartDateNextInvoice(Fleet fleet) {
        // TODO Perhaps overtime replace this by a max function in dao sql database
        LocalDateTime startDate=fleet.getCreatedAt();
        Collection<Invoice> previousInvoices = context.getInvoiceDao().findByFleet(fleet);
        if (!previousInvoices.isEmpty()){
            for (Invoice item: previousInvoices) {
                if (item.getEndDate().isAfter(startDate)){
                    startDate=item.getStartDate();
                }
            }
        }
        return startDate;
    }


    private class PremiumCalculator {

        /**
         * Calculate premium of a vehicle subscription from a given date.
         * All contracts linked to the subscription which are still active after the given date
         * will be included.
         * @param f the fleet subscription
         * @param startDate start date from where contracts have to be included
         * @return calculated premium
         */
        BigDecimal calculatePremium(FleetSubscription f, LocalDateTime startDate,LocalDateTime endDate) {
            BigDecimal totalAmount = BigDecimal.ZERO;
            Collection<Contract> contracts =f.getContracts();
            contracts = contracts.stream().filter((c) -> c.getEndDate().isAfter(startDate)&&
                    c.getStartDate().isBefore(endDate)).collect(Collectors.toSet());

            for (Contract contract: contracts) {
                BigDecimal premium = BigDecimal.valueOf(contract.getPremium());
                if (contract.getStartDate().isAfter(startDate) || contract.getEndDate().isBefore(endDate)){
                    LocalDateTime maxStart = contract.getStartDate().isAfter(startDate) ? contract.getStartDate() : startDate;
                    LocalDateTime minEnd = contract.getEndDate().isAfter(endDate) ? endDate:contract.getEndDate();
                    premium = premium.divide(BigDecimal.valueOf(ChronoUnit.DAYS.between(startDate, endDate)), RoundingMode.FLOOR)
                            .multiply(BigDecimal.valueOf(ChronoUnit.DAYS.between(maxStart, minEnd)));
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
         *  calls the method calculatePremium for each subscription
         * @param fleet
         * @param startDate
         * @return
         */
        BigDecimal calculateTotalAmount(Fleet fleet, LocalDateTime startDate) {
            BigDecimal totalAmount =BigDecimal.ZERO;
            LocalDateTime endDate = startDate.plusMonths(fleet.getFacturationPeriod());
            Collection<VehicleType> vehicleTypes = context.getVehicleTypeDao().findAll();
            for (VehicleType vehicleType: vehicleTypes) {
                Collection<FleetSubscription> subscriptionsWithVehicleType = context.getFleetSubscriptionDao()
                        .fleetSubscriptionByFleetAndVehicleTypeWithStartDateAndEndDate(fleet,vehicleType,endDate.toLocalDate())
                        .stream().filter(c->c.getEndDate()==null || c.getEndDate().isAfter(startDate.toLocalDate())).collect(Collectors.toSet());

                for (FleetSubscription fleetSubscription: subscriptionsWithVehicleType) {
                    totalAmount = totalAmount.add(calculatePremium(fleetSubscription,startDate,endDate));

                }
            }
            return totalAmount;
        }
    }
}