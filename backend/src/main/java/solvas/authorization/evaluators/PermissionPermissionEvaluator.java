package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Permission;

public class PermissionPermissionEvaluator extends AbstractPermissionEvaluator<Permission> {
    public PermissionPermissionEvaluator(Dao<Permission> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Permission model) {
        return hasScope(authentication, "read:auth:permissions");
    }

    @Override
    public boolean canCreate(Authentication authentication, Permission model) {
        return hasScope(authentication, "write:auth:permissions");
    }

    @Override
    public boolean canEdit(Authentication authentication, Permission model) {
        return hasScope(authentication, "write:auth:permissions");
    }

    @Override
    public boolean canDelete(Authentication authentication, Permission model) {
        return hasScope(authentication, "write:auth:permissions");
    }
}
