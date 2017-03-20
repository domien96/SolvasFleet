package solvas.persistence.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.models.FleetSubscription;
import solvas.models.Vehicle;
import solvas.persistence.api.Filter;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Niko Strijbol
 */
@Component
public class FleetSubscriptionDaoImpl implements CustomFleetSubscription {

    private static final String VEHICLE_ATTRIBUTE = "vehicle";

    @Autowired
    private FleetSubscriptionDao dao;

    @Override
    public Optional<FleetSubscription> activeForVehicle(Vehicle vehicle) {
        Filter<FleetSubscription> filter = Filter.predicate((builder, root) -> {
            LocalDate now = LocalDate.now();
            // The start must be before today
            Predicate start = builder.lessThanOrEqualTo(root.get("startDate"), now);
            // The end is not set or after today
            Expression<LocalDate> endDate = root.get("endDate");

            return builder.and(
                    builder.equal(root.get(VEHICLE_ATTRIBUTE), vehicle),
                    start,
                    builder.or(
                            builder.isNull(endDate),
                            builder.greaterThan(endDate, now)
                    ));
        });

        // TODO normally you cannot have multiple of these.
        Collection<FleetSubscription> results = dao.findAll(filter.toSpecification());
        if (results.size() >= 1) {
            return Optional.ofNullable(results.iterator().next());
        } else {
            return Optional.empty();
        }
    }
}
