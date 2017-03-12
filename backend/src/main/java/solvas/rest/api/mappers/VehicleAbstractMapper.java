package solvas.rest.api.mappers;


import org.springframework.stereotype.Component;
import solvas.models.Company;
import solvas.models.Vehicle;
import solvas.persistence.DaoContext;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.logic.GetVehicleToCompany;
import solvas.rest.logic.InconsistentDbException;
import solvas.rest.logic.LinkVehicleCompany;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class VehicleAbstractMapper extends AbstractMapper<Vehicle,ApiVehicle> {


    private String rootPath="/vehicles/";
    /**
     * TODO document
     *
     * @param daoContext
     */
    public VehicleAbstractMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Vehicle convertToModel(ApiVehicle api) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(api.getId());

        if (vehicle.getId()!=0){

            vehicle = daoContext.getVehicleDao().find(vehicle.getId());
            if (vehicle==null){
                vehicle= new Vehicle();
            }
        }


        vehicle.setLicensePlate(api.getLicensePlate()==null
                ? vehicle.getLicensePlate() : api.getLicensePlate());
        vehicle.setChassisNumber(api.getChassisNumber()==null
                ? vehicle.getChassisNumber() : api.getChassisNumber());
        vehicle.setModel(api.getModel()==null
                ? vehicle.getModel() : api.getModel());
        vehicle.setKilometerCount(api.getMileage()==0
                ? vehicle.getKilometerCount() : api.getMileage());


        vehicle.setYear(api.getYear()==0
                ? vehicle.getYear() : api.getYear());
        vehicle.setLeasingCompany(api.getLeasingCompany()==0
                ? vehicle.getLeasingCompany() :daoContext.getCompanyDao().find(api.getLeasingCompany()));
        vehicle.setValue(0);//api.getValue()

        vehicle.setBrand(api.getBrand()==null
                ? vehicle.getBrand() : api.getBrand());
        vehicle.setType(api.getType()==null ? vehicle.getType() :
                new VehicleTypeAbstractMapper(daoContext).convertToModel(api.getType()));

        //create link between company and vehicle
        if (api.getCompany()!=0) {

            vehicle = daoContext.getVehicleDao().save(vehicle);
            generateLinkVehicleCompany(api.getCompany(),vehicle);
            //TODO save vehicle first then save active subscription
        }

        vehicle.setUpdatedAt(null);
        vehicle.setCreatedAt(vehicle.getCreatedAt());
        return vehicle;
    }


    @Override
    public ApiVehicle convertToApiModel(Vehicle vehicle) {
        ApiVehicle api = new ApiVehicle();
        api.setId(vehicle.getId());
        api.setId(vehicle.getId());
        api.setLicensePlate(vehicle.getLicensePlate());
        api.setChassisNumber(vehicle.getChassisNumber());
        api.setModel(vehicle.getModel());
        api.setMileage(vehicle.getKilometerCount());
        api.setYear(vehicle.getYear());
        api.setLeasingCompany(vehicle.getLeasingCompany()==null ? 0 :vehicle.getLeasingCompany().getId());
        api.setValue(0);//api.getValue()
        api.setBrand(vehicle.getBrand());
        api.setCompany(getApiCompany(vehicle));
        api.setType(vehicle.getType().getName());
        api.setUpdatedAt(vehicle.getUpdatedAt());
        api.setCreatedAt(vehicle.getCreatedAt());
        api.setUrl(rootPath+api.getId());
        return api;
    }

    private void generateLinkVehicleCompany(int companyId, Vehicle v){
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
