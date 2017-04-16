package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiPermission;
import solvas.rest.query.PermissionFilter;
import solvas.service.mappers.AbstractMapper;
import solvas.service.models.Permission;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Service layer for permissions
 */
@Service
public class PermissionService extends AbstractService<Permission, ApiPermission> {
    private DaoContext daoContext;

    /**
     * Contruct an abstractservice
     *
     * @param daoContext the DAO context
     * @param mapper   the mapper between the apimodel and the model
     */
    @Autowired
    public PermissionService(DaoContext daoContext, AbstractMapper<Permission, ApiPermission> mapper) {
        super(daoContext.getPermissionDao(), mapper);
        this.daoContext = daoContext;
    }

    /**
     * Allow to add filtering by role to filter
     * @param filter The filter to add filtering to
     * @param roleId The id of the role to filter one
     * @throws EntityNotFoundException Role not found
     */
    public void filterOnRole(PermissionFilter filter, int roleId) throws EntityNotFoundException {
        Collection<Integer> ids = daoContext.getRoleDao().find(roleId).getPermissions().stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());
        filter.setIds(ids);
    }
}
