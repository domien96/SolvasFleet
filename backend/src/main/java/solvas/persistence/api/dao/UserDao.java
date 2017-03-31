package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.service.models.User;
import solvas.persistence.api.Dao;

/**
 * DAO for users.
 *
 * @author Steven Bastiaens
 * @author Niko Strijbol
 */
@Repository
public interface UserDao extends Dao<User> {

    /**
     * Find a single user by his/her email
     * @param email The email to search for
     * @return The associated user entity
     */
    User getByEmail(String email);
}