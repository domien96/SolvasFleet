package solvas.persistence.company;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Company;
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
public class HibernateCompanyDao extends HibernateDao<Company> implements CompanyDao {

    /**
     * Hibernate implementation for Company.
     */
    public HibernateCompanyDao() {
        super(Company.class);
    }

    @Override
    public Collection<Company> withName(String name) {
        return run(s -> {
            // Criteria builder
            CriteriaBuilder builder = s.getCriteriaBuilder();
            // Select from the company table
            CriteriaQuery<Company> criteriaQuery = builder.createQuery(Company.class);
            Root<Company> root = criteriaQuery.from(Company.class);
            // Actual criteria
            Predicate predicate = builder.equal(root.get("name"), name);
            // Prepare query
            criteriaQuery.select(root).where(predicate);
            // Do the query
            return s.createQuery(criteriaQuery).getResultList();
        });
    }
}