package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.Role;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiRole;

/**
 * Mapper between Role and ApiRole
 */
@Component
public class RoleAbstractMapper extends AbstractMapper<Role,ApiRole> {

    private final String rootPath="/roles/";
    /**
     * Create a mapper between Role and ApiRole
     *
     * @param daoContext The DaoContext this mapper should work with
     */
    public RoleAbstractMapper(DaoContext daoContext) {
        super(daoContext, "startDate", "function", "endDate");
    }

    @Override
    public Role convertToModel(ApiRole api) throws FieldNotFoundException,EntityNotFoundException {

        Role role = new Role();
        role.setId(api.getId());
        if (role.getId()!=0) {
            //update
            role = daoContext.getRoleDao().find(role.getId());
        }

        copySharedAttributes(role, api);
        role.setUser(api.getUser()==0 ? role.getUser() : daoContext.getUserDao().find(api.getUser()));
        role.setCompany(api.getCompany()==0 ? role.getCompany() : daoContext.getCompanyDao().find(api.getCompany()));
        //role permissions
        return role;
    }

    @Override
    public ApiRole convertToApiModel(Role role) throws FieldNotFoundException {
        ApiRole apiRole = new ApiRole();
        copyAttributes(apiRole, role, "id");
        copySharedAttributes(apiRole, role);
        apiRole.setUrl(rootPath+apiRole.getId());
        apiRole.setCompany(role.getCompany().getId());
        apiRole.setUser(role.getUser().getId());
        return apiRole;
    }
}
