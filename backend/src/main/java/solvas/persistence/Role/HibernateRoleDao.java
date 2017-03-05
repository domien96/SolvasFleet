package solvas.persistence.Role;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Role;
import solvas.persistence.HibernateDao;

/**
 * Hibernate implementation for role.
 *
 * @author steven
 */
@Repository
@Transactional
public class HibernateRoleDao extends HibernateDao<Role> implements RoleDao {


    /**
     * Hibernate implementation for Role.
     */
    public HibernateRoleDao() {
        super(Role.class);
    }
}
