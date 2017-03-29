package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.service.models.Role;
import solvas.persistence.api.Dao;

/**
 * DAO for roles.
 *
 * @author Steven Bastiaens
 * @author Niko Strijbol
 */
@Repository
public interface RoleDao extends Dao<Role> {

}