package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiRole;
import solvas.rest.utils.SimpleUrlBuilder;
import solvas.service.mappers.exceptions.FieldNotFoundException;
import solvas.service.models.Permission;
import solvas.service.models.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper between Role and ApiRole
 */
@Component
public class RoleMapper extends AbstractMapper<Role,ApiRole> {

    private static final String ROOTPATH ="/auth/roles/";
    /**
     * Create a mapper between Role and ApiRole
     *
     * @param daoContext The DaoContext this mapper should work with
     */
    public RoleMapper(DaoContext daoContext) {
        super(daoContext,"function");
    }

    @Override
    public Role convertToModel(ApiRole api) throws FieldNotFoundException,EntityNotFoundException {
        Role role = api.getId()==0?new Role():daoContext.getRoleDao().find(api.getId());
        copySharedAttributes(role, api);
        role.setPermissions(new HashSet<>(daoContext.getPermissionDao().findAll(api.getPermissions())));

        return role;
    }

    @Override
    public ApiRole convertToApiModel(Role role) throws FieldNotFoundException {
        ApiRole apiRole = new ApiRole();
        copyAttributes(apiRole, role, "id", "createdAt", "updatedAt");
        copySharedAttributes(apiRole, role);
        Set<Integer> apiPermissions = role.getPermissions().stream()
                .map(Permission::getId).collect(Collectors.toSet());
        apiRole.setPermissions(apiPermissions);
        apiRole.setUrl(SimpleUrlBuilder.buildUrlFromBase(ROOTPATH + "{id}", role.getId()));
        return apiRole;
    }
}
