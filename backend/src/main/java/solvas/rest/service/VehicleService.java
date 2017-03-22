package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.*;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.DependantEntityNotFound;
import solvas.rest.api.mappers.VehicleMapper;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiVehicle;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * VehicleService Class
 */
@Service
public class VehicleService extends AbstractService<Vehicle,ApiVehicle>{
    private static final String FLEET_ATTRIBUTE = "fleet";

    @Autowired
    private DaoContext context;
    @Autowired
    public VehicleService(DaoContext context, VehicleMapper mapper) {
        super(context.getVehicleDao(), mapper);
    }

    /**
     * We override the default update method, because we have to add several attributes to the model
     * @return The new vehicle
     */
    @Override
    public ApiVehicle update(int id,ApiVehicle input)
    {
        input.setId(id);
        Vehicle vehicle = mapper.convertToModel(input);
        vehicle.setLeasingCompany(context.getCompanyDao().find(input.getLeasingCompany()));
        VehicleType type = (context.getVehicleTypeDao().withType(input.getType())).iterator().next();
        vehicle.setType(type);


        // Create a link between everything.
        if (input.getFleet() > -1) {

            LocalDate now = LocalDate.now();

            // TODO: do this without saving vehicle twice.
            context.getVehicleDao().save(vehicle);

            // TODO: split up the method below
            // TODO: improve error handling

            // Get active subscriptions
            Optional<FleetSubscription> present = context.getFleetSubscriptionDao().activeForVehicle(vehicle);
            // If this is not a new vehicle, adjust the older stuff if needed.
            if (present.isPresent()) {
                FleetSubscription subscription = present.get();
                // Remove the vehicle from the fleet if the ID is 0.
                if (input.getFleet() == 0) {
                    subscription.setEndDate(now);
                    context.getFleetSubscriptionDao().save(subscription);
                } else {
                    createSubscription(input, subscription, vehicle, now);
                }
            } else if (input.getFleet() > 0) {
                // If the code is 0, there was no active subscription, so do nothing.
                try {
                    // If there is no existing subscription, simply add a new one.
                    Fleet fleet = context.getFleetDao().find(input.getFleet());
                    linkFleet(vehicle, fleet, now);
                } catch (EntityNotFoundException e) {
                    throw new DependantEntityNotFound(FLEET_ATTRIBUTE, e);
                }
            }
        }
        return mapper.convertToApiModel(context.getVehicleDao().save(vehicle));
    }


    /**
     * Subscribe a Vehicle to a fleet
     * @param api The ApiVehicle corresponding to Vehicle
     * @param subscription The subscription model
     * @param vehicle The Vehicle to subscribe
     * @param date The time subscription ends
     */
    private void createSubscription(ApiVehicle api, FleetSubscription subscription, Vehicle vehicle, LocalDate date) {
        try {
            Fleet fleet = context.getFleetDao().find(api.getFleet());
            // This is a new subscription.
            Fleet subscriptionFleet = subscription.getSubFleet().getFleet();
            if (subscriptionFleet.getId() != fleet.getId()) {
                subscription.setEndDate(date);
                context.getFleetSubscriptionDao().save(subscription);
                // Add a new fleet
                linkFleet(vehicle, fleet, date);
            }
        } catch (EntityNotFoundException e) {
            throw new DependantEntityNotFound(FLEET_ATTRIBUTE, e);
        }
    }
    /**
     * Ensure a link between a vehicle and a fleet.
     *
     * @param vehicle The vehicle.
     * @param fleet   The fleet.
     * @param now     The current date.
     */
    private void linkFleet(Vehicle vehicle, Fleet fleet, LocalDate now) {

        // Check for subfleet
        Collection<SubFleet> subFleets = context.getSubFleetDao().withFleetId(fleet.getId());
        // Filter if we already have a subtype or not.
        Optional<SubFleet> maybeFleet = subFleets.stream()
                .filter(s -> vehicle.getType().getName().equals(s.getVehicleType().getName()))
                .findFirst();

        SubFleet subFleet = maybeFleet.orElseGet(() -> {
            SubFleet newFleet = new SubFleet();
            newFleet.setFleet(fleet);
            newFleet.setVehicleType(vehicle.getType());
            return context.getSubFleetDao().save(newFleet);
        });

        FleetSubscription subscription = new FleetSubscription();
        subscription.setStartDate(now);
        subscription.setSubFleet(subFleet);
        subscription.setVehicle(vehicle);

        context.getFleetSubscriptionDao().save(subscription);
    }
}
