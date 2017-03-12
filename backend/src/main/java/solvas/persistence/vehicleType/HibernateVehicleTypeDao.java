package solvas.persistence.vehicleType;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.VehicleType;
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
public class HibernateVehicleTypeDao extends HibernateDao<VehicleType> implements VehicleTypeDao {

    /**
     * Hibernate implementation for Company.
     */
    public HibernateVehicleTypeDao() {
        super(VehicleType.class);
    }

    @Override
    public VehicleType withType(String name) {
        return run(s -> {
            // Criteria builder
            CriteriaBuilder builder = s.getCriteriaBuilder();
            // Select from the company table
            CriteriaQuery<VehicleType> criteriaQuery = builder.createQuery(VehicleType.class);
            Root<VehicleType> root = criteriaQuery.from(VehicleType.class);
            // Actual criteria
            Predicate predicate = builder.equal(root.get("name"), name);
            // Prepare query
            criteriaQuery.select(root).where(predicate);
            // Do the query
            return s.createQuery(criteriaQuery).getResultList().get(0);
        });
    }
}