package solvas.persistence.fleetSubscription;

import solvas.models.FleetSubscription;
import solvas.persistence.Dao;

import java.util.Collection;

public interface FleetSubscriptionDao extends Dao<FleetSubscription> {
    Collection<FleetSubscription> withVehicleId(int vehicleId);
    Collection<FleetSubscription> withFleetId(int subFleetId);
}