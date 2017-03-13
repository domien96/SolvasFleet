package solvas.rest.api.mappers;


import org.springframework.stereotype.Component;
import solvas.models.Company;
import solvas.models.Vehicle;
import solvas.persistence.DaoContext;
import solvas.persistence.EntityNotFoundException;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.logic.GetVehicleToCompany;
import solvas.rest.logic.InconsistentDbException;
import solvas.rest.logic.LinkVehicleCompany;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class VehicleMapper extends AbstractMapper<Vehicle,ApiVehicle> {

    private DaoContext daoContext;
    /**
     * TODO document
     *
     * @param daoContext
     */
    public VehicleMapper(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    @Override
    public Vehicle convertToModel(ApiVehicle api) throws EntityNotFoundException, FieldNotFoundException {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(api.getId());

        if (vehicle.getId()!=0){
            vehicle = daoContext.getVehicleDao().find(vehicle.getId());
        }

        copyAttributes(vehicle, api, "licensePlate", "chassisNumber", "model", "mileage", "year", "brand", "createdAt");

        vehicle.setLeasingCompany(api.getLeasingCompany()==0
                ? vehicle.getLeasingCompany() :daoContext.getCompanyDao().find(api.getLeasingCompany()));
        vehicle.setValue(0);//api.getValue()

        vehicle.setType(api.getType()==null ? vehicle.getType() :
                new VehicleTypeMapper(daoContext).convertToModel(api.getType()));

        //create link between company and vehicle
        if (api.getCompany()!=0) {

            vehicle = daoContext.getVehicleDao().save(vehicle);
            generateLinkVehicleCompany(api.getCompany(),vehicle);
            //TODO save vehicle first then save active subscription
        }

        return vehicle;
    }


    @Override
    public ApiVehicle convertToApiModel(Vehicle vehicle) throws FieldNotFoundException {
        ApiVehicle api = new ApiVehicle();

        copyAttributes(api, vehicle, "id", "licensePlate", "chassisNumber", "model", "mileage", "year", "brand", "createdAt");

        api.setLeasingCompany(vehicle.getLeasingCompany()==null ? 0 :vehicle.getLeasingCompany().getId());

        api.setCompany(getApiCompany(vehicle));
        api.setType(vehicle.getType().getName());
        return api;
    }

    private void generateLinkVehicleCompany(int companyId, Vehicle v) throws EntityNotFoundException {
        //TODO
        //diference between company change detect it and handle it
        try {
            new LinkVehicleCompany().run(companyId,v,
                    daoContext.getFleetSubscriptionDao(),
                    daoContext.getSubFleetDao(),
                    daoContext.getFleetDao(),daoContext.getCompanyDao());
        } catch (InconsistentDbException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param vehicle
     * @return returns 0 if there are no active Subscriptions.
     */
    private int getApiCompany(Vehicle vehicle){
        int companyId;
        try {
            Company company = new GetVehicleToCompany().run(vehicle,daoContext.getFleetSubscriptionDao());
            companyId =company.getId();
        } catch (InconsistentDbException e) {
            e.printStackTrace(); //Should not happen
            companyId=0;
        } catch (GetVehicleToCompany.NoActiveSubscriptionException e) {
            companyId=0;
        }
        return companyId;
    }
}
