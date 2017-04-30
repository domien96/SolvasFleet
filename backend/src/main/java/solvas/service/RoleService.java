package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FunctionDao;
import solvas.service.exceptions.UndeletableException;
import solvas.service.models.Function;
import solvas.service.models.Role;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.RoleMapper;
import solvas.rest.api.models.ApiRole;

import java.util.Collection;

/**
 * RoleService class
 */
@Service
public class RoleService extends AbstractService<Role,ApiRole>{

    private final FunctionDao functionDao;

    /**
     * Construct a FleetService
     * @param context the DaoContext
     * @param mapper the mapper to map ApiRole and Role
     */
    @Autowired
    public RoleService(DaoContext context, RoleMapper mapper) {
        super(context.getRoleDao(), mapper);
        functionDao = context.getFunctionDao();
    }

    @Override
    public void destroy(int id) throws EntityNotFoundException, UndeletableException {

        // Get the role
        Role role = modelDao.find(id);

        // Check for active functions.
        Collection<Function> activeFunctions = functionDao.findByRoleAndArchivedFalse(role);

        //  If there are active functions, don't delete.
        if (!activeFunctions.isEmpty()) {
            throw new UndeletableException();
        }

        // Otherwise destroy the role. The database will cascade this to the functions.
        modelDao.destroy(role);
    }
}
