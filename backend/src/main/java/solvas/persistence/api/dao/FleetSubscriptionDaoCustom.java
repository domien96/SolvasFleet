package solvas.persistence.api.dao;

import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import java.time.LocalDate;
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
     * Get the active subscription for a period with a start date and end date.
     *
     * An active subscription is defined as a subscription for which the start date lies before or on given date,
     * and there is no end date or the end date is on or after the given date.
     *
     * This means: active => startDate <= date1 && (endDate == null || endDate >= date2)
     *
     * In this method, date1 = start and date2 = end.
     *
     * @param vehicle The vehicle.
     * @param start The start date. Inclusive.
     * @param end The end date. Inclusive.
     */
    Optional<FleetSubscription> activeForVehicleBetween(Vehicle vehicle, LocalDate start, LocalDate end);
}