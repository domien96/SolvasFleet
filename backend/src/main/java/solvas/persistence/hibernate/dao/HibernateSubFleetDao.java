package solvas.persistence.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Fleet;
import solvas.models.SubFleet;
import solvas.persistence.api.Filter;
import solvas.persistence.api.dao.SubFleetDao;
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
public class HibernateSubFleetDao extends HibernateDao<SubFleet> implements SubFleetDao {

    /**
     * Hibernate implementation for Company.
     */
    public HibernateSubFleetDao() {
        super(SubFleet.class);
    }

    @Override
    public Collection<SubFleet> withFleetId(int fleetId) {
        return findAll(Filter.predicate((builder, root) -> {
            Join<SubFleet, Fleet> join = root.join("fleet");
            return builder.equal(
                    join.get("id"),
                    fleetId
            );
        }));
    }
}