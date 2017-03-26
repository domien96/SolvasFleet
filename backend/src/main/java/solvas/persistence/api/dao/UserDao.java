package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.models.User;
import solvas.persistence.api.Dao;

/**
 * DAO for users.
 *
 * @author Steven Bastiaens
 * @author Niko Strijbol
 */
@Repository
public interface UserDao extends Dao<User> {

}