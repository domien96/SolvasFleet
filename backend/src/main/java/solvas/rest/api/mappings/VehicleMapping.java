package solvas.rest.api.mappings;


import solvas.models.Company;
import solvas.models.FleetSubscription;
import solvas.models.Vehicle;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.logic.InconsistentDbException;
import solvas.rest.logic.VehicleToCompany;

import java.util.Collection;

/**
 * Created by steve on 11/03/2017.
 */
public class VehicleMapping extends Mapping<Vehicle,ApiVehicle> {

    @Override
    public Vehicle convertToModel(ApiVehicle api) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(api.getId());
        vehicle.setLicensePlate(api.getLicensePlate());
        vehicle.setChassisNumber(api.getChassisNumber());
        vehicle.setModel(api.getModel());
        vehicle.setKilometerCount(api.getMileage());
        vehicle.setYear(api.getYear());
        vehicle.setLeasingCompany(companyDao.find(api.getLeasingCompany()));
        vehicle.setValue(0);//api.getValue()
        vehicle.setBrand(api.getBrand());
        //vehicle.setFleetSubscription(getFleetSubscription(api));
        vehicle.setType(new VehicleTypeMapping().convertToModel(api.getType()));
        vehicle.setUpdatedAt(api.getUpdatedAt());
        vehicle.setCreatedAt(api.getCreatedAt());
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
        api.setLeasingCompany(vehicle.getLeasingCompany().getId());
        api.setValue(0);//api.getValue()
        api.setBrand(vehicle.getBrand());
        api.setCompany(getApiCompany(vehicle));
        api.setType(vehicle.getType().getName());
        api.setUpdatedAt(vehicle.getUpdatedAt());
        api.setCreatedAt(vehicle.getCreatedAt());
        return null;
    }

    private Collection<FleetSubscription> getFleetSubscription(ApiVehicle api){
        //if new vehicle
        //new fleet subscription

        // new subfleet

        //new fleet

        //Get company : vehicle -> fleet subscription -> sub fleet -> fleet


        return null;
    }


    /**
     *
     * @param vehicle
     * @return returns 0 if there are no active Subscriptions.
     */
    private int getApiCompany(Vehicle vehicle){
        int companyId;
        try {
            Company company = new VehicleToCompany().run(vehicle,fleetSubscriptionDao);
            companyId =company.getId();
        } catch (InconsistentDbException e) {
            e.printStackTrace(); //Should not happen
            companyId=0;
        } catch (VehicleToCompany.NoActiveSubscriptionException e) {
            companyId=0;
        }
        return companyId;
    }
}
