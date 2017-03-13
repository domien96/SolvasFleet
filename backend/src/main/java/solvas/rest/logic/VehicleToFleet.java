package solvas.rest.logic;

import solvas.models.Fleet;
import solvas.models.FleetSubscription;
import solvas.models.Vehicle;
import solvas.persistence.Filter;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.Collection;

/**
 * VehicleToFleet is a helper class part of the backend logic/service layer. This specific class helps when trying to
 * retrieve the fleet in which a vehicle is a part of. Since our model(db) is different than the model of the Api,
 * a conversion is required. A vehicle has no direct relation with a fleet, so one must be fetched. This class will
 * fetch the route from a vehicle to a fleet, eventually returning the fleet.
 *
 *
 * Todo find a better way to model logic
 *
 * @author sjabasti
 * @author nistrijb
 */
public class VehicleToFleet {

    /**
     * This will fetch the fleet of a vehicle
     * @param vehicle The vehicle of which the fleet has to be returned
     * @param fleetSubscriptionDao dao needed for this complex operation
     * @return Fleet of which the vehicle is part of
     * @throws InconsistentDbException any inconsistencies in the database will result in this error
     * @throws NoActiveSubscriptionException Trying to get the fleet of a vehicle while there are no active subscriptions
     */
    public Fleet run(Vehicle vehicle, FleetSubscriptionDao fleetSubscriptionDao) throws InconsistentDbException, NoActiveSubscriptionException {

        // We want all active subscriptions for this vehicle.
        Filter<FleetSubscription> filter = Filter.predicate((builder, root) -> {
            LocalDate now = LocalDate.now();
            // The start must be before today
            Predicate start = builder.lessThanOrEqualTo(root.get("startDate"), now);
            // Get active subscriptions for this vehicle.
            Join<FleetSubscription, Vehicle> join = root.join("vehicle");
            // The end is not set or after today
            Expression<LocalDate> endDate = root.get("endDate");
            Predicate end = builder.or(
                    builder.isNull(endDate),
                    builder.greaterThan(endDate, now)
            );

            return builder.and(start, end, builder.equal(join.get("id"), vehicle.getId()));
        });

        Collection<FleetSubscription> activeSubscriptions = fleetSubscriptionDao.findAll(filter);

        // There can only be one active subscription at a time.
        if (activeSubscriptions.size() > 1) {
            throw new InconsistentDbException();
        }
        // If there is no active subscription
        if (activeSubscriptions.size() <= 0) {
            throw new NoActiveSubscriptionException();
        }

        return activeSubscriptions.iterator().next().getSubFleet().getFleet();
    }

    /**
     * NoActiveSubscriptionException is a exception on the logic/service layer and is part of the retrieval process
     * of a fleet from a vehicle. It means that the vehicle is currently not register to a fleet.
     */
    public class NoActiveSubscriptionException extends Exception{

    }
}