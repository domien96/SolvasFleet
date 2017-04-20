package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Permission;

import static solvas.authorization.ApiPermissionStrings.READ_AUTH_PERMISSIONS;
import static solvas.authorization.ApiPermissionStrings.WRITE_AUTH_PERMISSIONS;

/**
 * Evaluate permissions for permissions.
 */
public class PermissionPermissionEvaluator extends AbstractPermissionEvaluator<Permission> {

    /**
     * @param dao Autowired dao.
     */
    public PermissionPermissionEvaluator(Dao<Permission> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Permission model) {
        return hasScope(authentication, READ_AUTH_PERMISSIONS);
    }

    @Override
    public boolean canCreate(Authentication authentication, Permission model) {
        return hasScope(authentication, WRITE_AUTH_PERMISSIONS);
    }

    @Override
    public boolean canEdit(Authentication authentication, Permission model) {
        return hasScope(authentication, WRITE_AUTH_PERMISSIONS);
    }

    @Override
    public boolean canDelete(Authentication authentication, Permission model) {
        return hasScope(authentication, WRITE_AUTH_PERMISSIONS);
    }
}
