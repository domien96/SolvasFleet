package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Role;

public class RolePermissionEvaluator extends AbstractPermissionEvaluator<Role> {
    {
        registerPermissionDecider("LIST_PERMISSIONS", this::canListPermissions);
    }

    public RolePermissionEvaluator(Dao<Role> dao) {
        super(dao);
    }

    public boolean canReadPermissions(Authentication authentication, Role model) {
        return hasScope(authentication, "read:auth:roles");
    }

    @Override
    public boolean canRead(Authentication authentication, Role model) {
        return hasScope(authentication, "read:auth:roles");
    }

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
