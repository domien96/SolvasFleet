package solvas.persistence.api.dao;

import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Custom methods to implement because JPA can't
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
     * Get the active subscription for a period [start, end[.
     *
     * A subscription is considered active if {@link FleetSubscription#isActive()} would return true if it was called
     * on any moment of the [start, end[ interval.
     *
     * @param vehicle The vehicle.
     * @param start The start date. Inclusive.
     * @param end The end date. Inclusive.
     *
     * @return The optional fleet subscription.
     */
    Optional<FleetSubscription> activeForVehicleBetween(Vehicle vehicle, LocalDateTime start, LocalDateTime end);
}