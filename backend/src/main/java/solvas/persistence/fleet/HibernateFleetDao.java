package solvas.persistence.fleet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Company;
import solvas.models.Fleet;
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
public class HibernateFleetDao extends HibernateDao<Fleet> implements FleetDao {

    /**
     * Hibernate implementation for Company.
     */
    public HibernateFleetDao() {
        super(Fleet.class);
    }

    @Override
    public Collection<Fleet> withCompanyId(int companyId) {
        return run(s -> {
            // Criteria builder
            CriteriaBuilder builder = s.getCriteriaBuilder();
            // Select from the company table
            CriteriaQuery<Fleet> criteriaQuery = builder.createQuery(Fleet.class);
            Root<Fleet> root = criteriaQuery.from(Fleet.class);
            // Actual criteria
            Predicate predicate = builder.equal(root.get("company"), companyId);
            // Prepare query
            criteriaQuery.select(root).where(predicate);
            // Do the query
            return s.createQuery(criteriaQuery).getResultList();
        });
    }
}