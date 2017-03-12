package solvas.persistence.fleet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Fleet;
import solvas.persistence.Filter;
import solvas.persistence.HibernateDao;

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