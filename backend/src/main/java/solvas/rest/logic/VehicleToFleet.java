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
 * Created by steve on 11/03/2017.
 *
 */
public class VehicleToFleet {

    public Fleet run(Vehicle vehicle, FleetSubscriptionDao fleetSubscriptionDao) throws InconsistentDbException, NoActiveSubscriptionException {

        // We want all active subscriptions for this vehicle.
        Filter<FleetSubscription> filter = Filter.predicate((builder, root) -> {
            LocalDate now = LocalDate.now();
            // The start must be before today
            Predicate start = builder.lessThan(root.get("startDate"), now);
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

    public class NoActiveSubscriptionException extends Exception{

    }
}