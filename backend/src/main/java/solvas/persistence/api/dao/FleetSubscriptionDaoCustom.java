package solvas.persistence.api.dao;

import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

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