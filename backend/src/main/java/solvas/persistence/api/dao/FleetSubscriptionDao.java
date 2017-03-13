package solvas.persistence.api.dao;

import solvas.models.FleetSubscription;
import solvas.persistence.api.Dao;

import java.util.Collection;

public interface FleetSubscriptionDao extends Dao<FleetSubscription> {
    Collection<FleetSubscription> withVehicleId(int vehicleId);
    Collection<FleetSubscription> withFleetId(int subFleetId);
}