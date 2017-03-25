package solvas.persistence.api.dao;

import solvas.models.FleetSubscription;
import solvas.models.Vehicle;

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
}