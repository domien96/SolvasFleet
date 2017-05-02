package solvas.persistence.api.dao;

import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

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
}