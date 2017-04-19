package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.InsuranceTypeDao;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.mappers.InvoiceMapper;
import solvas.service.models.*;


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

    // Helper for calculaitng premiums
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
        ApiInvoice invoice = new ApiInvoice();

        // fleet is given as parameter
        // Read fleet from database
        Fleet fleet = context.getFleetDao().find(fleetId);
        // if the fleet is not found, it will throw a EntityNotFoundException, which will be caught by
            //the method not found in AbstractController
        invoice.setFleet(fleet.getId()); // or just use the parameter

        // Set the startDate for this invoice
        invoiceSetStartDate(invoice,fleet);


        //invoice enddate = startend date  + periode
        invoice.setEndDate(invoice.getStartDate().plusMonths(3)); //TODO replace by fleet.get period


        // paid == false, as it is active
        invoice.setPaid(false);


        //TODO review each type how to calculate
        // Calculate totalamount
        //step 1 get all vehicle types
        BigDecimal totalAmount =BigDecimal.ZERO;

        Collection<VehicleType> vehicleTypes = context.getVehicleTypeDao().findAll();
        for (VehicleType vehicleType: vehicleTypes) {
            Collection<FleetSubscription> subscriptionsWithVehicleType = context.getFleetSubscriptionDao()
                    .fleetSubscriptionByFleetAndVehicleTypeAfterStartDate(fleet,vehicleType,invoice.getStartDate());
            for (FleetSubscription fleetSubscription: subscriptionsWithVehicleType) {
                // contract
                // TODO handle case when enddate != null
                totalAmount = totalAmount.add(premiumCalc.calculatePremium(fleetSubscription,invoice.getStartDate()));

                }
        }
        invoice.setTotalAmount(totalAmount.longValue());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());



        // set ype of invoice
        invoice.setType(type.getText());


        return invoice;
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
        invoice.setAmount(premiumCalc.calculateTotalAmount());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());
        if(endDate.isBefore(LocalDateTime.now())) {
            context.getInvoiceDao().save(invoice);
        }
        return invoice;
    }

    /**
     * invoice startdate = date fleet was made or the end date of last generated invoice
     * @param invoice
     * @param fleet
     */
    private void invoiceSetStartDate(ApiInvoice invoice,Fleet fleet) {
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
        invoice.setStartDate(startDate);
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
     * @param fleet
     * @return the start date
     */
    public LocalDateTime getStartDateNextInvoice(Fleet fleet) {
        return null; //todo
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
        BigDecimal calculatePremium(FleetSubscription f, LocalDateTime startDate) {
            BigDecimal totalAmount = BigDecimal.ZERO;
            Collection<Contract> contracts =f.getContracts();
            contracts = contracts.stream().filter((c) -> {
                return c.getEndDate().isAfter(startDate);

            }).collect(Collectors.toSet());

            for (Contract contract: contracts) {
                // Todo handle startdate in between as wel
                // TODO -if (contract.getEndDate().isAfter(invoice.getEndDate()))- check needed?
                int premium = contract.getPremium();
                Tax tax = context.getTaxDao().findDistinctByVehicleTypeNameAndInsuranceTypeName(
                        f.getVehicle().getType().getName(), contract.getInsuranceType().getName());


                totalAmount = totalAmount.add((tax.getTax().add(BigDecimal.ONE)).multiply(BigDecimal.valueOf(premium)));
            }
            return totalAmount;
        }

        BigDecimal calculateTotalAmount() {
            BigDecimal totalAmount =BigDecimal.ZERO;

            Collection<VehicleType> vehicleTypes = context.getVehicleTypeDao().findAll();
            for (VehicleType vehicleType: vehicleTypes) {
                Collection<FleetSubscription> subscriptionsWithVehicleType = context.getFleetSubscriptionDao()
                        .fleetSubscriptionByFleetAndVehicleTypeAfterStartDate(fleet,vehicleType,invoice.getStartDate());
                for (FleetSubscription fleetSubscription: subscriptionsWithVehicleType) {
                    // contract
                    // TODO handle case when enddate != null
                    totalAmount = totalAmount.add(calculatePremium(fleetSubscription,invoice.getStartDate()));

                }
            }

            return totalAmount;
        }
    }
}