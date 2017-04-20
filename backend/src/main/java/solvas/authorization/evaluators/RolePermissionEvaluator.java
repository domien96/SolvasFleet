package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Role;

/**
 * Evaluate permissions for roles.
 */
public class RolePermissionEvaluator extends AbstractPermissionEvaluator<Role> {
    {
        registerPermissionDecider("LIST_PERMISSIONS", this::canListPermissions);
    }

    /**
     * @param dao Autowired dao.
     */
    public RolePermissionEvaluator(Dao<Role> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Role model) {
        return hasScope(authentication, "read:auth:roles");
    }

    /**
     * Check if the user can view permissions for a role.
     *
     * @param authentication The authentication.
     * @param model The role.
     *
     * @return True if the user has permission.
     */
    public boolean canListPermissions(Authentication authentication, Role model) {
        return hasScope(authentication, "read:auth:roles");
    }

    @Override
    public boolean canCreate(Authentication authentication, Role model) {
        return hasScope(authentication, "write:auth:roles");
    }

    @Override
    public boolean canEdit(Authentication authentication, Role model) {
        return hasScope(authentication, "write:auth:roles");
    }

    @Override
    public boolean canDelete(Authentication authentication, Role model) {
        return hasScope(authentication, "write:auth:roles");
    }
}
