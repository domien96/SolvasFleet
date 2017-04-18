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
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * InvoiceService class
 */
@Service
public class InvoiceService extends AbstractService<Invoice,ApiInvoice> {

    //Dao for generating a response for findAllInsuranceTypes
    private InsuranceTypeDao insuranceTypeDao;
    private DaoContext context;

    /**
     * Construct an abstract service
     *
     * @param context the DAO context
     * @param mapper   the mapper between the api model and the model
     */
    @Autowired
    public InvoiceService(DaoContext context, InvoiceMapper mapper) {
        super(context.getInvoiceDao(), mapper);
        insuranceTypeDao= context.getInsuranceTypeDao();
        this.context=context;
    }


    /**
     * Finds all types of insurance in the database
     * @return types of insurance
     */
    public Collection<String> findAllInsuranceTypes() {
        return insuranceTypeDao.findAll().stream().map(InsuranceType::getName).collect(Collectors.toSet());

    }

    public ApiInvoice findActiveInvoiceByType(int fleetId,String type) throws EntityNotFoundException{
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
                Collection<Contract> contracts =fleetSubscription.getContracts();
                final LocalDateTime finalStartDate=invoice.getStartDate();
                contracts = contracts.stream().filter((c) -> {
                    return c.getEndDate().isAfter(finalStartDate);

                }).collect(Collectors.toSet());

                for (Contract contract: contracts) {
                    // Todo handle startdate in between as wel
                    if (contract.getEndDate().isAfter(invoice.getEndDate())){
                        int prem = contract.getPremium();
                        Tax tax = context.getTaxDao().findDistinctByVehicleTypeNameAndInsuranceTypeName(vehicleType.getName(), contract.getInsuranceType().getName());


                        totalAmount=totalAmount.add(
                                (context.getTaxDao().findDistinctByVehicleTypeNameAndInsuranceTypeName(vehicleType.getName(),contract.getInsuranceType().getName())
                                        .getTax().add(BigDecimal.ONE)).multiply(BigDecimal.valueOf(contract.getPremium())));


                    } else {
                        // Stops in the middle

                    }

                }
            }
        }
        invoice.setTotalAmount(totalAmount.longValue());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());



        // set ype of invoice
        invoice.setType(type);


        return invoice;
    }

    private ApiInvoice callculateInvoiceTypeBilling(Fleet fleet, LocalDateTime beginDate,LocalDateTime endDate) {

        return null;
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

}