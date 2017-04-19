package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;
import solvas.persistence.api.Dao;
import solvas.service.models.VehicleType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * DAO for fleet subscriptions.
 *
 * @author Niko Strijbol
 * @author Steven Bastiaens
 */
@Repository
public interface FleetSubscriptionDao extends Dao<FleetSubscription> {

    /**
     * Similar to {@link #activeForVehicle(Vehicle)}, but returns all subscriptions.
     *
     * @param vehicle The vehicle to find subscriptions for.
     *
     * @return The subscriptions.
     */
    Collection<FleetSubscription> findByVehicle(Vehicle vehicle);
    FleetSubscription findByVehicleAndArchivedFalseAndStartDateLessThanEqualOrStartDateIsNull(Vehicle vehicle, LocalDate startDate);

    Collection<FleetSubscription> findAllByFleetAndStartDateGreaterThanEqual(Fleet fleet, LocalDate startDate);
    /**
     * Get all subscriptions
     * @param fleet The fleet
     * @param vehicleType The vehicle
     * @param startDate The startdate
     * @return a list of all subscriptions with fleet and vehicletype
     */
    Collection<FleetSubscription> findAllByFleetAndVehicleTypeAndStartDateGreaterThanEqual(Fleet fleet, VehicleType vehicleType, LocalDate startDate) ;


    Collection<FleetSubscription> findAllByFleetAndVehicleTypeAndStartDateLessThan(Fleet fleet, VehicleType vehicleType, LocalDate endDate) ;


    /**
     * Get the active subscription for a vehicle.
     *
     * @param vehicle The vehicle.
     *
     * @return The optional subscription.
     */
    default Optional<FleetSubscription> activeForVehicle(Vehicle vehicle) {
        return Optional.ofNullable(findByVehicleAndArchivedFalseAndStartDateLessThanEqualOrStartDateIsNull(vehicle, LocalDate.now()));
    }

    default Collection<FleetSubscription> fleetSubscriptionByFleetAndVehicleTypeAfterStartDate(Fleet fleet, VehicleType vehicleType, LocalDate startDate) {
        return findAllByFleetAndVehicleTypeAndStartDateGreaterThanEqual(fleet, vehicleType, startDate);
    }

    default Collection<FleetSubscription> fleetSubscriptionByFleetAndVehicleTypeWithStartDateAndEndDate(Fleet fleet, VehicleType vehicleType,LocalDate endDate) {
        return findAllByFleetAndVehicleTypeAndStartDateLessThan(fleet, vehicleType,endDate);
    }
}