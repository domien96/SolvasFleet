package solvas.persistence.api.dao;

import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;
import solvas.service.models.VehicleType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * Custom methods for {@link FleetSubscriptionDao}.
 *
 * @author Niko Strijbol
 */
public interface FleetSubscriptionDaoCustom {

    /**
     * Get the active subscription for a vehicle.
     *
     * @param vehicle The vehicle.
     *
     * @return The optional subscription.
     */
    Optional<FleetSubscription> activeForVehicle(Vehicle vehicle);

    /**
     * Get all subscriptions
     * @param fleet The fleet
     * @param vehicleType The vehicle
     * @param startDate The startdate
     * @return a list of all subscriptions with fleet and vehicletype
     */
    Collection<FleetSubscription> fleetSubscriptionByFleetAndVehicleTypeAfterStartDate(Fleet fleet, VehicleType vehicleType, LocalDateTime startDate);
}