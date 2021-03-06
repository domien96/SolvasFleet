package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.User;

import static solvas.authorization.ApiPermissionStrings.*;

/**
 * Evaluate user-related permissions.
 */
public class UserPermissionEvaluator extends AbstractPermissionEvaluator<User> {
    {
        registerPermissionDecider("READ_ROLES", this::canReadRoles);
    }

    /**
     * @param dao Autowired dao.
     */
    public UserPermissionEvaluator(Dao<User> dao) {
        super(dao);
    }

    /**
     * Check if a user can read the roles for a user
     * @param authentication Current user auth
     * @param model User to read roles for
     * @return boolean
     */
    public boolean canReadRoles(Authentication authentication, User model) {
        return hasScope(authentication, "read:users") || isSameUser(authentication, model);
    }

    private boolean isSameUser(Authentication authentication, User user) {
        return user != null &&
                        (Integer.valueOf(authentication.getName())).equals(user.getId());
    }
    @Override
    public boolean canRead(Authentication authentication, User model) {
        return hasScope(authentication, READ_USERS) || isSameUser(authentication, model);
    }

    @Override
    public boolean canCreate(Authentication authentication, User model) {
        return hasScope(authentication, CREATE_USERS);
    }

    @Override
    public boolean canEdit(Authentication authentication, User model) {
        return hasScope(authentication, WRITE_USERS) || isSameUser(authentication, model);
    }

    @Override
    public boolean canDelete(Authentication authentication, User model) {
        return hasScope(authentication, WRITE_USERS);
    }
}
