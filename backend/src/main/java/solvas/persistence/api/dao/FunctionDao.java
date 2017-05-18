package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Company;
import solvas.service.models.Function;
import solvas.service.models.Role;
import solvas.service.models.User;

import java.util.Collection;

/**
 * DAO for functions.
 */
@Repository
public interface FunctionDao extends Dao<Function> {

    /**
     * Find all active functions for a certain role.
     *
     * @param role The role.
     *
     * @return The roles.
     */
    Collection<Function> findByRoleAndArchivedFalse(Role role);

    /**
     * Find al active functions for a certain company
     * @param company the company
     * @return the functions
     */
    Collection<Function> findByCompanyAndArchivedFalse(Company company);

    /**
     * Find al active functions for a certain user
     * @param user the company
     * @return the functions
     */
    Collection<Function> findByUserAndArchivedFalse(User user);
}
