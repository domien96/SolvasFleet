package solvas.persistence.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.FleetSubscription;
import solvas.models.SubFleet;
import solvas.models.Vehicle;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.persistence.hibernate.HibernateDao;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * Hibernate implementation for Company.
 *
 * @author Niko Strijbol
 */
@Repository
@Transactional
public class HibernateFleetSubscriptionDao extends HibernateDao<FleetSubscription> implements FleetSubscriptionDao {

    /**
     * Hibernate implementation for Company.
     */
    public HibernateFleetSubscriptionDao() {
        super(FleetSubscription.class);
    }

    @Override
    public Collection<FleetSubscription> withVehicleId(int vehicleId) {
        return findAll(Filter.predicate((builder, root) -> {
            Join<FleetSubscription, Vehicle> join = root.join("vehicle");
            return builder.equal(
                    join.get("id"),
                    vehicleId
            );
        }));
    }

    @Override
    public Collection<FleetSubscription> withFleetId(int subFleetId) {
        return findAll(Filter.predicate((builder, root) -> {
            Join<FleetSubscription, SubFleet> join = root.join("subFleet");
            return builder.equal(
                    join.get("id"),
                    subFleetId
            );
        }));
    }

    @Override
    public Optional<FleetSubscription> activeForVehicle(Vehicle vehicle) {
        Filter<FleetSubscription> filter = Filter.predicate((builder, root) -> {
            LocalDate now = LocalDate.now();
            // The start must be before today
            Predicate start = builder.lessThanOrEqualTo(root.get("startDate"), now);
            // The end is not set or after today
            Expression<LocalDate> endDate = root.get("endDate");

            return builder.and(
                    builder.equal(root.get("vehicle"), vehicle),
                    start,
                    builder.or(
                            builder.isNull(endDate),
                            builder.greaterThan(endDate, now)
                    ));
        });

        // TODO normally you cannot have multiple of these.
        Collection<FleetSubscription> results = findAll(filter);
        if (results.size() >= 1) {
            return Optional.ofNullable(results.iterator().next());
        } else {
            return Optional.empty();
        }
    }
}