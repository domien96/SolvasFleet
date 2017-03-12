package solvas.persistence.subFleet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Company;
import solvas.models.SubFleet;
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
public class HibernateSubFleetDao extends HibernateDao<SubFleet> implements SubFleetDao {

    /**
     * Hibernate implementation for Company.
     */
    public HibernateSubFleetDao() {
        super(SubFleet.class);
    }

    @Override
    public Collection<SubFleet> withFleetId(int fleetId) {
            // Criteria builder
            CriteriaBuilder builder = getSession().getCriteriaBuilder();
            // Select from the company table
            CriteriaQuery<SubFleet> criteriaQuery = builder.createQuery(SubFleet.class);
            Root<SubFleet> root = criteriaQuery.from(SubFleet.class);
            // Actual criteria
            Predicate predicate = builder.equal(root.get("fleet"), fleetId);
            // Prepare query
            criteriaQuery.select(root).where(predicate);
            // Do the query
            return getSession().createQuery(criteriaQuery).getResultList();
    }
}