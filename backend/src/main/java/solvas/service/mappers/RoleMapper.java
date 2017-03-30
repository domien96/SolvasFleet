package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.service.models.Role;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.exceptions.FieldNotFoundException;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.SimpleUrlBuilder;
import solvas.rest.api.models.ApiRole;

/**
 * Mapper between Role and ApiRole
 */
@Component
public class RoleMapper extends AbstractMapper<Role,ApiRole> {

    private static final String ROOTPATH ="/roles/";
    /**
     * Create a mapper between Role and ApiRole
     *
     * @param daoContext The DaoContext this mapper should work with
     */
    public RoleMapper(DaoContext daoContext) {
        super(daoContext, "startDate", "function", "endDate");
    }

    @Override
    public Role convertToModel(ApiRole api) throws FieldNotFoundException,EntityNotFoundException {
        Role role = api.getId()==0?new Role():daoContext.getRoleDao().find(api.getId());
        copySharedAttributes(role, api);
        /*try {  The api allows -1 will leave this here for the future
            role.setUser(daoContext.getUserDao().find(api.getUser()));
            role.setCompany(daoContext.getCompanyDao().find(api.getCompany()));
        } catch(EntityNotFoundException e) {
            throw new DependantEntityNotFound("Company or User not found",e);
        }*/

        role.setUser(api.getUser()==0 ? role.getUser() : daoContext.getUserDao().find(api.getUser()));
        role.setCompany(api.getCompany()==0 ? role.getCompany() : daoContext.getCompanyDao().find(api.getCompany()));
        return role;
    }

    @Override
    public ApiRole convertToApiModel(Role role) throws FieldNotFoundException {
        ApiRole apiRole = new ApiRole();
        copyAttributes(apiRole, role, "id");
        copySharedAttributes(apiRole, role);
        apiRole.setCompany(role.getCompany().getId());
        apiRole.setUser(role.getUser().getId());
        apiRole.setUrl(SimpleUrlBuilder.buildUrlFromBase(ROOTPATH + "{id}", role.getId()));
        return apiRole;
    }
}
