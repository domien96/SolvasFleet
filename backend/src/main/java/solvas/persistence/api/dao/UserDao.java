package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.models.User;
import solvas.persistence.api.Dao;

import java.util.Optional;

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
     *
     * @param email The email to search for
     * @return The associated user entity
     */
    Optional<User> getByEmail(String email);

    /**
     * Find a single unarchived user by his/her email.
     *
     * @param email The email to search for
     * @return The associated user entity
     */
    Optional<User> getByEmailAndArchivedFalse(String email);

    /**
     * Find a single user by his/her email
     *
     * @param email The email to search for
     * @return The associated user entity
     * @throws EntityNotFoundException User not found
     */
    default User findByEmail(String email) throws EntityNotFoundException {
        return getByEmail(email)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Could not find user with email %s.", email))
                );
    }

    /**
     * Find a single user by his/her email
     *
     * @param email The email to search for
     * @return The associated user entity
     * @throws EntityNotFoundException User not found
     */
    default User findUnarchivedByEmail(String email) throws EntityNotFoundException {
        return getByEmailAndArchivedFalse(email)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Could not find user with email %s.", email))
                );
    }
}