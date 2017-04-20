package solvas.service.mappers;


import org.springframework.stereotype.Component;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.utils.SimpleUrlBuilder;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.mappers.exceptions.FieldNotFoundException;
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

    private static final String ROOTPATH = "/vehicles/";

    /**
     * Maps a apiVehicle on our own vehicles
     *
     * @param daoContext context for connecting entity's
     */
    public VehicleMapper(DaoContext daoContext) {
        super(daoContext, "id", "licensePlate", "model", "year", "value", "brand");
    }


    @Override
    public Vehicle convertToModel(ApiVehicle api) throws DependantEntityNotFound,
            FieldNotFoundException, EntityNotFoundException {
        final Vehicle vehicle=api.getId()==0 ? new Vehicle() : daoContext.getVehicleDao().find(api.getId());
        copySharedAttributes(vehicle, api);
        vehicle.setKilometerCount(api.getMileage()); //Todo replace kilometerCount to mileage so it is a shared Attribute
        vehicle.setChassisNumber(api.getVin());

        if (api.getLeasingCompany() != 0) {
            vehicle.setLeasingCompany(daoContext.getCompanyDao().find(api.getLeasingCompany()));
        }

        vehicle.setType(new VehicleTypeMapper(daoContext).convertToModel(api.getType()));

        // Create a link between everything.
        if (api.getFleet() > -1) {

            LocalDate now = LocalDate.now();

            // TODO: do this without saving vehicle twice.
            daoContext.getVehicleDao().save(vehicle);

            // TODO: split up the method below
            // TODO: improve error handling

            // Get active subscriptions
            Optional<FleetSubscription> present = daoContext.getFleetSubscriptionDao().activeForVehicle(vehicle);
            // If this is not a new vehicle, adjust the older stuff if needed.
            if (present.isPresent()) {
                FleetSubscription subscription = present.get();
                // Remove the vehicle from the fleet if the ID is 0.
                if (api.getFleet() == 0) {
                    subscription.setEndDate(now);
                    daoContext.getFleetSubscriptionDao().save(subscription);
                } else {
                    createSubscription(api, subscription, vehicle, now);
                }
            } else if (api.getFleet() > 0) {
                // If the code is 0, there was no active subscription, so do nothing.
                try {
                    // If there is no existing subscription, simply add a new one.
                    Fleet fleet = daoContext.getFleetDao().find(api.getFleet());
                    linkFleet(vehicle, fleet, now);
                } catch (EntityNotFoundException e) {
                    throw new DependantEntityNotFound(FLEET_ATTRIBUTE, e);
                }
            }
        }

        return vehicle;
    }

    /**
     * Subscribe a Vehicle to a fleet
     *
     * @param api          The ApiVehicle corresponding to Vehicle
     * @param subscription The subscription model
     * @param vehicle      The Vehicle to subscribe
     * @param date         The time subscription ends
     */
    private void createSubscription(ApiVehicle api, FleetSubscription subscription, Vehicle vehicle, LocalDate date) throws DependantEntityNotFound {
        try {
            Fleet fleet = daoContext.getFleetDao().find(api.getFleet());
            // This is a new subscription.
            Fleet subscriptionFleet = subscription.getFleet();
            if (subscriptionFleet.getId() != fleet.getId()) {
                subscription.setEndDate(date);
                daoContext.getFleetSubscriptionDao().save(subscription);
                // Add a new fleet
                linkFleet(vehicle, fleet, date);
            }
        } catch (EntityNotFoundException e) {
            throw new DependantEntityNotFound(FLEET_ATTRIBUTE, e);
        }
    }

    @Override
    public ApiVehicle convertToApiModel(Vehicle vehicle) throws FieldNotFoundException {
        ApiVehicle api = new ApiVehicle();
        copyAttributes( api, vehicle,"createdAt", "updatedAt");
        copySharedAttributes(api, vehicle);


        api.setMileage(vehicle.getKilometerCount());
        api.setVin(vehicle.getChassisNumber());
        api.setLeasingCompany(vehicle.getLeasingCompany() == null ? 0 : vehicle.getLeasingCompany().getId());

        api.setFleet(getApiFleet(vehicle));
        api.setType(vehicle.getType().getName());
        api.setUrl(SimpleUrlBuilder.buildUrlFromBase(ROOTPATH + "{id}", vehicle.getId()));
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
                .map(fleetSubscription -> fleetSubscription.getFleet().getId()).orElse(0);
    }

    /**
     * Ensure a link between a vehicle and a fleet.
     * Doesn't create new fleet subscription when fleet and vehicle were already linked.
     * If the vehicle already had another subscription, that subscription will be terminated and a new one
     * will be created and linked to the vehicle.
     * @param vehicle The vehicle.
     * @param fleet   The fleet.
     * @param now     The current date.
     */
    private void linkFleet(Vehicle vehicle, Fleet fleet, LocalDate now) throws EntityNotFoundException {
        Optional<FleetSubscription> fs = daoContext.getFleetSubscriptionDao().activeForVehicle(vehicle);
        if(fs.isPresent()) {
            if (fs.get().getFleet().equals(fleet)) {
                return;
            } else {
                fs.get().setEndDate(now);
            }
        }
        FleetSubscription subscription = new FleetSubscription();
        subscription.setStartDate(now);
        subscription.setFleet(fleet);
        subscription.setVehicle(vehicle);

        daoContext.getFleetSubscriptionDao().save(subscription);
    }
}
