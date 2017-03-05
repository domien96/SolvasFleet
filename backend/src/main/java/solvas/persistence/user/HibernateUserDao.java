package solvas.persistence.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.User;
import solvas.persistence.HibernateDao;

/**
 * Created by steve on 04/03/2017.
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
