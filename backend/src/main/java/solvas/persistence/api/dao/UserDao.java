package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.EntityNotFoundException;
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

    /**
     * Find a single user by his/her email
     * @param email The email to search for
     * @return The associated user entity
     * @throws EntityNotFoundException User not found
     */
    default User findByEmail(String email) throws EntityNotFoundException {
        User u = getByEmail(email);
        if(u == null) {
            throw new EntityNotFoundException(String.format("Could not find user with email %s.", email));
        }

        return u;
    }
}