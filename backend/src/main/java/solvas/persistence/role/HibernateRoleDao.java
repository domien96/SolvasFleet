package solvas.persistence.role;

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
     * Hibernate implementation for role.
     */
    public HibernateRoleDao() {
        super(Role.class);
    }
}
