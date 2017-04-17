package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.InsuranceTypeDao;
import solvas.rest.api.models.ApiContract;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.api.models.ApiModel;
import solvas.service.mappers.ContractMapper;
import solvas.service.mappers.InvoiceMapper;
import solvas.service.models.*;


import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by steve on 15/04/2017.
 */
public class InvoiceService extends AbstractService<Invoice,ApiInvoice> {

    //Dao for generating a response for findAllInsuranceTypes
    private InsuranceTypeDao insuranceTypeDao;
    private DaoContext context;

    /**
     * Contruct an abstractservice
     *
     * @param context the DAO context
     * @param mapper   the mapper between the apimodel and the model
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

    public ApiInvoice findActiveInvoice(int fleetId) throws EntityNotFoundException{
        ApiInvoice invoice = new ApiInvoice();

        // fleet is given as parameter
        // Read fleet from database
        Fleet fleet = context.getFleetDao().find(fleetId);
        // if the fleet is not found, it will throw a EntityNotFoundException, which will be caught by
            //the method not found in AbstractController
        invoice.setFleet(fleet.getId()); // or just use the parameter

        //invoice startdate = date fleet was made or the end date of last generated invoice
        LocalDateTime startDate=fleet.getCreatedAt();
        // TODO Perhaps overtime replace this by a max function in dao sql database
        Collection<Invoice> previousInvoices = context.getInvoiceDao().findByFleet(fleet);
        if (!previousInvoices.isEmpty()){
            for (Invoice item: previousInvoices) {
                if (item.getEndDate().isAfter(startDate)){
                    startDate=item.getStartDate();
                }
            }
        }
        invoice.setStartDate(startDate);

        //invoice enddate = startend date  + periode
        invoice.setEndDate(startDate.plusMonths(3)); //TODO replace by fleet.get period


        // paid == false, as it is active
        invoice.setPaid(false);


        //TODO review each type how to calculate
        // Calculate totalamount
        //step 1 get all vehicle types
        BigDecimal totalAmount =BigDecimal.ZERO;

        Collection<VehicleType> vehicleTypes = context.getVehicleTypeDao().findAll();
        for (VehicleType vehicleType: vehicleTypes) {
            Collection<FleetSubscription> subscriptionsWithVehicleType = context.getFleetSubscriptionDao()
                    .fleetSubscriptionByFleetAndVehicleTypeAfterStartDate(fleet,vehicleType,startDate);
            for (FleetSubscription fleetSubscription: subscriptionsWithVehicleType) {
                // contract
                // TODO handle case when enddate != null
                Collection<Contract> contracts =fleetSubscription.getContracts();
                final LocalDateTime finalStartDate=startDate;
                contracts = contracts.stream().filter((c) -> {
                    return c.getEndDate().isAfter(finalStartDate);

                }).collect(Collectors.toSet());

                for (Contract contract: contracts) {
                    // Todo handle startdate in between as wel
                    if (contract.getEndDate().isAfter(invoice.getEndDate())){
                        totalAmount.add(
                                (context.getTaxDao().findDistinctByVehicleTypeNameAndInsuranceTypeName(vehicleType.getName(),contract.getInsuranceType().getName())
                                        .getTax().add(BigDecimal.ONE)).multiply(BigDecimal.valueOf(contract.getPremium())));


                    } else {
                        // Stops in the middle

                    }





                }

            }


            
        }




        // set ype of invoice



        return invoice;
    }
}