package solvas.persistence.api.dao;

import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

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
     * Find all subscription for a fleet in the period start-end
     * @param fleet
     * @param start
     * @param end
     * @return
     */
    Collection<FleetSubscription> findByFleetAndStartDateAndEndDate(Fleet fleet, LocalDate start, LocalDate end);
}
