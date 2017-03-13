package solvas.rest.api.mappers;


import org.springframework.stereotype.Component;
import solvas.models.Fleet;
import solvas.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.logic.VehicleToFleet;
import solvas.rest.logic.InconsistentDbException;
import solvas.rest.logic.LinkVehicleCompany;

/**
 * Mapper between Vehicle and ApiVehicle
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
        vehicle.setChassisNumber(api.getVin()==null
                ? vehicle.getChassisNumber() : api.getVin());
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
        if (api.getFleet()!=0) {

            vehicle = daoContext.getVehicleDao().save(vehicle);
            Fleet fleet = daoContext.getFleetDao().find(api.getFleet());
            generateLinkVehicleCompany(fleet.getCompany().getId(),vehicle);
            //TODO save vehicle first then save active subscription
        }

        return vehicle;
    }


    @Override
    public ApiVehicle convertToApiModel(Vehicle vehicle) {
        ApiVehicle api = new ApiVehicle();
        api.setId(vehicle.getId());
        api.setId(vehicle.getId());
        api.setLicensePlate(vehicle.getLicensePlate());
        api.setVin(vehicle.getChassisNumber());
        api.setModel(vehicle.getModel());
        api.setMileage(vehicle.getKilometerCount());
        api.setYear(vehicle.getYear());
        api.setLeasingCompany(vehicle.getLeasingCompany()==null ? 0 :vehicle.getLeasingCompany().getId());
        api.setValue(0);//api.getValue()
        api.setBrand(vehicle.getBrand());
        api.setFleet(getApiFleet(vehicle));
        api.setType(vehicle.getType().getName());
        api.setUpdatedAt(vehicle.getUpdatedAt());
        api.setCreatedAt(vehicle.getCreatedAt());
        api.setUrl(rootPath+api.getId());
        return api;
    }

    private void generateLinkVehicleCompany(int fleetId, Vehicle v){
        //TODO
        //diference between company change detect it and handle it
        try {
            new LinkVehicleCompany().run(fleetId,v, daoContext);
        } catch (InconsistentDbException | EntityNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param vehicle
     * @return returns 0 if there are no active Subscriptions.
     */
    private int getApiFleet(Vehicle vehicle){
        int fleetId;
        try {
            Fleet fleet = new VehicleToFleet().run(vehicle, daoContext);
            fleetId =fleet.getId();
        } catch (InconsistentDbException e) {
            e.printStackTrace(); //Should not happen
            fleetId=0;
        } catch (VehicleToFleet.NoActiveSubscriptionException e) {
            fleetId=0;
        }
        return fleetId;
    }
}
