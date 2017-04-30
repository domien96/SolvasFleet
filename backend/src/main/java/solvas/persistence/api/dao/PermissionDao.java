package solvas.persistence.api.dao;

import solvas.persistence.api.Dao;
import solvas.service.models.Permission;

import java.util.Collection;

/**
 * DAO for permissions
 */
public interface PermissionDao extends Dao<Permission> {

    /**
     * Find all permission where the scope is in the given parameter
     * @param scopes the scopes
     * @return Permissions with scope in scopes
     */
    Collection<Permission> findByScopeIn(Collection<String> scopes);
}
