package solvas.persistence.hibernate.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.User;
import solvas.persistence.hibernate.HibernateDao;
import solvas.persistence.api.dao.UserDao;

/**
 * Hibernate implementation for user.
 *
 * @author steven
 */
@Repository
@Transactional
public class HibernateUserDao extends HibernateDao<User> implements UserDao {


    /**
     * Hibernate implementation for User.
     */
    public HibernateUserDao() {
        super(User.class);
    }
}