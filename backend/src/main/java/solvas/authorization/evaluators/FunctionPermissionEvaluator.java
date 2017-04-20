package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Function;

import static solvas.authorization.ApiPermissionStrings.WRITE_USERS_ROLES;

/**
 * Evaluate function-related permissions.
 */
public class FunctionPermissionEvaluator extends AbstractPermissionEvaluator<Function> {

    /**
     * @param dao Autowired dao.
     */
    public FunctionPermissionEvaluator(Dao<Function> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Function model) {
        return false;
    }

    @Override
    public boolean canCreate(Authentication authentication, Function model) {
        return hasScope(authentication, WRITE_USERS_ROLES);
    }

    @Override
    public boolean canEdit(Authentication authentication, Function model) {
        return hasScope(authentication, WRITE_USERS_ROLES);
    }

    @Override
    public boolean canDelete(Authentication authentication, Function model) {
        return hasScope(authentication, WRITE_USERS_ROLES);
    }
}
