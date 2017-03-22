package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.Role;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiRole;

/**
 * Mapper between Role and ApiRole
 */
@Component
public class RoleMapper extends AbstractMapper<Role,ApiRole> {

    private String rootPath="/roles/";
    /**
     * Create a mapper between Role and ApiRole
     *
     * @param daoContext The DaoContext this mapper should work with
     */
    public RoleMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Role convertToModel(ApiRole api, Role role) {

        role.setStartDate(api.getStartDate());
        role.setFunction(api.getFunction());
        role.setEndDate(api.getEndDate());
        //role permissions
        return role;
    }

    @Override
    public Role convertToEmptyModel(ApiRole api) throws DependantEntityNotFound {
        return convertToModel(api,new Role());
    }

    @Override
    public ApiRole convertToApiModel(Role role) {
        ApiRole apiRole = new ApiRole();
        apiRole.setId(role.getId());
        apiRole.setUrl(rootPath+apiRole.getId());
        apiRole.setCompany(role.getCompany().getId());
        apiRole.setUser(role.getUser().getId());
        apiRole.setFunction(role.getFunction());
        apiRole.setStartDate(role.getStartDate());
        apiRole.setEndDate(role.getEndDate());
        return apiRole;
    }
}
