package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiPermission;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Permission;

/**
 * Mapper between {@link Permission}s in the persistence layer and {@link ApiPermission}s in the API layer
 */
@Component
public class PermissionMapper extends AbstractMapper<Permission, ApiPermission> {

    public PermissionMapper(DaoContext daoContext) {
        super(daoContext, "action", "resource");
    }

    @Override
    public Permission convertToModel(ApiPermission api) throws DependantEntityNotFound, EntityNotFoundException {
        Permission permission = new Permission();
        copySharedAttributes(permission, api);
        return permission;
    }

    @Override
    public ApiPermission convertToApiModel(Permission model) {
        ApiPermission api = new ApiPermission();
        copySharedAttributes(api, model);
        return api;
    }
}
