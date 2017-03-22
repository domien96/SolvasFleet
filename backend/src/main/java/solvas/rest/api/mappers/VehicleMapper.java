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
        Vehicle vehicle=api.getId()==0?new Vehicle():daoContext.getVehicleDao().find(api.getId());

        vehicle.setLicensePlate(api.getLicensePlate());
        vehicle.setChassisNumber(api.getVin());
        vehicle.setModel(api.getModel());
        vehicle.setKilometerCount(api.getMileage());
        vehicle.setYear(api.getYear());
        vehicle.setLeasingCompany(daoContext.getCompanyDao().find(api.getLeasingCompany()));
        vehicle.setValue(api.getValue());
        vehicle.setBrand(api.getBrand());
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
     * @param api The ApiVehicle corresponding to Vehicle
     * @param subscription The subscription model
     * @param vehicle The Vehicle to subscribe
     * @param date The time subscription ends
     */
    private void createSubscription(ApiVehicle api, FleetSubscription subscription, Vehicle vehicle, LocalDate date) {
        try {
            Fleet fleet = daoContext.getFleetDao().find(api.getFleet());
            // This is a new subscription.
            Fleet subscriptionFleet = subscription.getSubFleet().getFleet();
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

    /**
     * Ensure a link between a vehicle and a fleet.
     *
     * @param vehicle The vehicle.
     * @param fleet   The fleet.
     * @param now     The current date.
     */
    private void linkFleet(Vehicle vehicle, Fleet fleet, LocalDate now) {

        // Check for subfleet
        Collection<SubFleet> subFleets = daoContext.getSubFleetDao().withFleetId(fleet.getId());
        // Filter if we already have a subtype or not.
        Optional<SubFleet> maybeFleet = subFleets.stream()
                .filter(s -> vehicle.getType().getName().equals(s.getVehicleType().getName()))
                .findFirst();

        SubFleet subFleet = maybeFleet.orElseGet(() -> {
            SubFleet newFleet = new SubFleet();
            newFleet.setFleet(fleet);
            newFleet.setVehicleType(vehicle.getType());
            return daoContext.getSubFleetDao().save(newFleet);
        });

        FleetSubscription subscription = new FleetSubscription();
        subscription.setStartDate(now);
        subscription.setSubFleet(subFleet);
        subscription.setVehicle(vehicle);

        daoContext.getFleetSubscriptionDao().save(subscription);
    }
}
