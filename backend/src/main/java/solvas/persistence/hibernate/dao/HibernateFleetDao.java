package solvas.persistence.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Company;
import solvas.models.Fleet;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.FleetDao;
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
public class HibernateFleetDao extends HibernateDao<Fleet> implements FleetDao {

    /**
     * Hibernate implementation for Company.
     */
    public HibernateFleetDao() {
        super(Fleet.class);
    }

    @Override
    public Collection<Fleet> withCompanyId(int companyId) {
        return findAll(Filter.predicate((builder, root) -> {
            Join<Fleet, Company> join = root.join("company");
            return builder.equal(
                    join.get("id"),
                    companyId
            );
        }));
    }
}