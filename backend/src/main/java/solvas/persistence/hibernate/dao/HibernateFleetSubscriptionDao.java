package solvas.persistence.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.FleetSubscription;
import solvas.models.SubFleet;
import solvas.models.Vehicle;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.persistence.hibernate.HibernateDao;

import javax.persistence.criteria.Join;
import java.util.Collection;

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
}