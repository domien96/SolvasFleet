package solvas.rest.api.mappers;


import org.springframework.stereotype.Component;
import solvas.models.Fleet;
import solvas.models.FleetSubscription;
import solvas.models.SubFleet;
import solvas.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiVehicle;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Mapper between Vehicle and ApiVehicle
 */
@Component
public class VehicleMapper extends AbstractMapper<Vehicle, ApiVehicle> {

    private static final String FLEET_ATTRIBUTE = "fleet";

    private String rootPath = "/vehicles/";

    /**
     * TODO document
     *
     * @param daoContext
     */
    public VehicleMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Vehicle convertToModel(ApiVehicle api) throws DependantEntityNotFound {

        final Vehicle vehicle;
        if (api.getId() == 0) {
            vehicle = new Vehicle();
        } else {
            vehicle = daoContext.getVehicleDao().find(api.getId());
        }

        vehicle.setId(api.getId());
        vehicle.setLicensePlate(api.getLicensePlate());
        vehicle.setChassisNumber(api.getVin());
        vehicle.setModel(api.getModel());
        vehicle.setKilometerCount(api.getMileage());
        vehicle.setYear(api.getYear());
        vehicle.setValue(api.getValue());
        vehicle.setBrand(api.getBrand());
        //vehicle.setLeasingCompany(daoContext.getCompanyDao().find(api.getLeasingCompany()));
        //vehicle.setType(new VehicleTypeMapper(daoContext).convertToModel(api.getType()));

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
        api.setLeasingCompany(vehicle.getLeasingCompany() == null ? 0 : vehicle.getLeasingCompany().getId());
        api.setValue(vehicle.getValue());//api.getValue()
        api.setBrand(vehicle.getBrand());
        api.setFleet(getApiFleet(vehicle));
        api.setType(vehicle.getType().getName());
        api.setUpdatedAt(vehicle.getUpdatedAt());
        api.setCreatedAt(vehicle.getCreatedAt());
        api.setUrl(rootPath + api.getId());
        return api;
    }

    /**
     * @param vehicle The vehicle.
     * @return returns 0 if there are no active Subscriptions.
     */
    private int getApiFleet(Vehicle vehicle) {
        return daoContext
                .getFleetSubscriptionDao()
                .activeForVehicle(vehicle)
                .map(fleetSubscription -> fleetSubscription.getSubFleet().getFleet().getId()).orElse(0);
    }


}
