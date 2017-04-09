package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiPermission;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Permission;

@Component
public class PermissionMapper extends AbstractMapper<Permission, ApiPermission> {

    public PermissionMapper(DaoContext daoContext) {
        super(daoContext, "name");
    }

    @Override
    public Permission convertToModel(ApiPermission api) throws DependantEntityNotFound, EntityNotFoundException {
        Permission p = new Permission();
        return null;
    }

    @Override
    public ApiPermission convertToApiModel(Permission model) {
        return null;
    }
}
