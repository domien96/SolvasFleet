package solvas.persistence.api.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
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
 * @author David Vandorpe
 */
@Repository
public interface FleetSubscriptionDao extends Dao<FleetSubscription>, FleetSubscriptionDaoCustom {


    /**
     * Get the active subscription for a vehicle.
     *
     * @param vehicle The vehicle.
     *
     * @return The optional subscription.
     */
    Optional<FleetSubscription> activeForVehicle(Vehicle vehicle);
    /**
     * Similar to {@link #activeForVehicle(Vehicle)}, but returns all subscriptions.
     *
     * @param vehicle The vehicle to find subscriptions for.
     *
     * @return The subscriptions.
     */
    Collection<FleetSubscription> findByVehicle(Vehicle vehicle);

    /**
     * Find all subscriptions of a fleet where the startDate of the subscription >= the startDate in the parameter,
     * @param fleet
     * @param startDate
     * @return
     */
    @Deprecated //This function is no longer used
    Collection<FleetSubscription> findAllByFleetAndStartDateGreaterThanEqual(Fleet fleet, LocalDate startDate);


    /**
     * Get all subscriptions of a fleet, with the given vehicle type and where startDate of the subscription is >= to the
     *      startDate of the given parameter
     * @param fleet The fleet
     * @param vehicleType The vehicle
     * @param startDate The startdate
     * @return a list of all subscriptions with fleet and vehicletype
     */
    @Deprecated //This function is no longer used
    Collection<FleetSubscription> findAllByFleetAndVehicleTypeAndStartDateGreaterThanEqual(Fleet fleet, VehicleType vehicleType, LocalDate startDate) ;

    /**
     * Get all subscriptions of a fleet, with the given vehicle type and where the startDate of the subscription
     *      is less then the provided date
     * @param fleet fleet of which the subscriptions need to be found
     * @param vehicleType type of vehicle of which the vehicle of a subscription belongs to
     * @param endDate the endDate
     * @return a collection of subscriptions which meet the requirements given in the above description
     */
    Collection<FleetSubscription> findAllByFleetAndVehicleTypeAndStartDateLessThan(Fleet fleet, VehicleType vehicleType, LocalDate endDate) ;

    /**
     * Find all subscriptions of a given fleet, where the vehicle of the subscription is of the given vehicle type and
     *      where the startDate of the subscription is >= the provided startDate
     * @param fleet fleet of which the subscriptions need to be found
     * @param vehicleType type of vehicle of which the vehicle of a subscription belongs to
     * @param startDate the endDate
     * @return a collection of subscriptions which meet the requirements given in the above description
     */
    @Deprecated //This function is no longer used
    default Collection<FleetSubscription> fleetSubscriptionByFleetAndVehicleTypeAfterStartDate(Fleet fleet, VehicleType vehicleType, LocalDate startDate) {
        return findAllByFleetAndVehicleTypeAndStartDateGreaterThanEqual(fleet, vehicleType, startDate);
    }

    /**
     * Find all subscriptions of a given fleet, where the vehicle of the subscription is of the given vehicle type and
     *      the startDate of the subscription is
     * @param fleet
     * @param vehicleType type of vehicle of which the vehicle of a subscription belongs to
     * @param endDate the endDate
     * @return
     */
    default Collection<FleetSubscription> fleetSubscriptionByFleetAndVehicleTypeWithStartDateAndEndDate(Fleet fleet, VehicleType vehicleType,LocalDate endDate) {
        return findAllByFleetAndVehicleTypeAndStartDateLessThan(fleet, vehicleType,endDate);
    }
}