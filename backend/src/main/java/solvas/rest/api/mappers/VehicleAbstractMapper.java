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
    public Vehicle convertToModel(ApiVehicle api) throws DependantEntityNotFound {

        final Vehicle vehicle;
        if (api.getId() == 0) {
            vehicle = new Vehicle();
        } else {
            vehicle = daoContext.getVehicleDao().find(api.getId());
        }

        vehicle.setId(api.getId());


        if (api.getLicensePlate() != null) {
            vehicle.setLicensePlate(api.getLicensePlate());
        }
        if (api.getVin() != null) {
            vehicle.setChassisNumber(api.getVin());
        }
        if (api.getModel() != null) {
            vehicle.setModel(api.getModel());
        }
        if (api.getMileage() != 0) {
            vehicle.setKilometerCount(api.getMileage());
        }
        if (api.getYear() != 0) {
            vehicle.setYear(api.getYear());
        }
        if (api.getLeasingCompany() != 0) {
            vehicle.setLeasingCompany(daoContext.getCompanyDao().find(api.getLeasingCompany()));
        }
        if (api.getValue() != 0) {
            vehicle.setValue(api.getValue());
        }
        if (api.getBrand() != null) {
            vehicle.setBrand(api.getBrand());
        }
        if (api.getType() != null) {
            vehicle.setType(new VehicleTypeAbstractMapper(daoContext).convertToModel(api.getType()));
        }

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
                    try {
                        Fleet fleet = daoContext.getFleetDao().find(api.getFleet());
                        // This is a new subscription.
                        Fleet subscriptionFleet = subscription.getSubFleet().getFleet();
                        if (subscriptionFleet.getId() != fleet.getId()) {
                            subscription.setEndDate(now);
                            daoContext.getFleetSubscriptionDao().save(subscription);
                            // Add a new fleet
                            linkFleet(vehicle, fleet, now);
                        }
                    } catch (EntityNotFoundException e) {
                        throw new DependantEntityNotFound("fleet", e);
                    }
                }
            } else {
                // If the code is 0, there was no active subscription, so do nothing.
                if (api.getFleet()  > 0) {
                    try {
                        // If there is no existing subscription, simply add a new one.
                        Fleet fleet = daoContext.getFleetDao().find(api.getFleet());
                        linkFleet(vehicle, fleet, now);
                    } catch (EntityNotFoundException e) {
                        throw new DependantEntityNotFound("fleet", e);
                    }
                }
            }
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

    /**
     *
     * @param vehicle The vehicle.
     *
     * @return returns 0 if there are no active Subscriptions.
     */
    private int getApiFleet(Vehicle vehicle){
        return daoContext
                .getFleetSubscriptionDao()
                .activeForVehicle(vehicle)
                .map(fleetSubscription -> fleetSubscription.getSubFleet().getFleet().getId()).orElse(0);
    }

    /**
     * Ensure a link between a vehicle and a fleet.
     *
     * @param vehicle The vehicle.
     * @param fleet The fleet.
     * @param now The current date.
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
