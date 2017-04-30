package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import java.util.Collection;

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
     * Similar to {@link #activeForVehicle(Vehicle)}, but returns all subscriptions.
     *
     * @param vehicle The vehicle to find subscriptions for.
     *
     * @return The subscriptions.
     */
    Collection<FleetSubscription> findByVehicle(Vehicle vehicle);
}