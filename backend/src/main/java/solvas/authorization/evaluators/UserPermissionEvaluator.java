package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.User;

public class UserPermissionEvaluator extends AbstractPermissionEvaluator<User> {
    {
        registerPermissionDecider("READ_ROLES", this::canReadRoles);
    }
    public UserPermissionEvaluator(Dao<User> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, User model) {
        return hasScope(authentication, "read:users") || model.getEmail().equals(authentication.getName());
    }

    public boolean canReadRoles(Authentication authentication, User model) {
        return hasScope(authentication, "read:users") || model.getEmail().equals(authentication.getName());
    }

    @Override
    public boolean canCreate(Authentication authentication, User model) {
        return hasScope(authentication, "create:users");
    }

    @Override
    public boolean canEdit(Authentication authentication, User model) {
        return hasScope(authentication, "write:users") || model.getEmail().equals(authentication.getName());
    }

    @Override
    public boolean canDelete(Authentication authentication, User model) {
        return hasScope(authentication, "write:users");
    }
}
