package solvas.persistence.api.dao;

import solvas.models.User;
import solvas.persistence.api.Dao;

/**
 * DAO for a User.
 * Created by steve on 04/03/2017.
 */
public interface UserDao extends Dao<User> {

    /**
     * Find a User by username
     * @param username Username to search for
     * @return The User with this username
     */
    User getByUsername(String username);

}