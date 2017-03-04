package solvas.persistence.Role;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Role;
import solvas.models.User;
import solvas.persistence.HibernateDao;

/**
 * Created by steve on 04/03/2017.
 */
@Repository
@Transactional
public class HibernateRoleDao extends HibernateDao<Role> implements RoleDao {


    protected HibernateRoleDao() {
        super(Role.class);
    }
}
