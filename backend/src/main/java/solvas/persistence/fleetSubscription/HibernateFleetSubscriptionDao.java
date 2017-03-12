package solvas.persistence.fleetSubscription;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Company;
import solvas.models.FleetSubscription;
import solvas.persistence.HibernateDao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        return run(s -> {
            // Criteria builder
            CriteriaBuilder builder = s.getCriteriaBuilder();
            // Select from the company table
            CriteriaQuery<FleetSubscription> criteriaQuery = builder.createQuery(FleetSubscription.class);
            Root<FleetSubscription> root = criteriaQuery.from(FleetSubscription.class);
            // Actual criteria
            Predicate predicate = builder.equal(root.get("vehicle"), vehicleId);
            // Prepare query
            criteriaQuery.select(root).where(predicate);
            // Do the query
            return s.createQuery(criteriaQuery).getResultList();
        });
    }

    @Override
    public Collection<FleetSubscription> withFleetId(int subFleetId) {
        return run(s -> {
            // Criteria builder
            CriteriaBuilder builder = s.getCriteriaBuilder();
            // Select from the company table
            CriteriaQuery<FleetSubscription> criteriaQuery = builder.createQuery(FleetSubscription.class);
            Root<FleetSubscription> root = criteriaQuery.from(FleetSubscription.class);
            // Actual criteria
            Predicate predicate = builder.equal(root.get("subFleet"), subFleetId);
            // Prepare query
            criteriaQuery.select(root).where(predicate);
            // Do the query
            return s.createQuery(criteriaQuery).getResultList();
        });
    }


}