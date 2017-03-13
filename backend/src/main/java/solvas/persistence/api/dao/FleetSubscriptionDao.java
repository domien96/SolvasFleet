package solvas.persistence.api.dao;

import solvas.models.FleetSubscription;
import solvas.persistence.api.Dao;

import java.util.Collection;

public interface FleetSubscriptionDao extends Dao<FleetSubscription> {
    /**
     * Find fleetsubscriptions for a vehicle
     * @param vehicleId The id of the vehicle
     * @return Collection of subscriptions
     */
    Collection<FleetSubscription> withVehicleId(int vehicleId);

    /**
     * Find fleetsubscriptions for a subfleet
     * @param subFleetId The id of the subfleet
     * @return Collection of subscriptions
     */
    Collection<FleetSubscription> withFleetId(int subFleetId);
}