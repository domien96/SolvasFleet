package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import solvas.models.Role;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiRole;

/**
 * Mapper between Role and ApiRole
 */
@Component
public class RoleAbstractMapper extends AbstractMapper<Role,ApiRole> {

    private String rootPath="/roles/";
    /**
     * Create a mapper between Role and ApiRole
     *
     * @param daoContext The DaoContext this mapper should work with
     */
    public RoleAbstractMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Role convertToModel(ApiRole api) throws EntityNotFoundException {
        Role role = new Role();
        role.setId(api.getId());
        if (role.getId()!=0) {
            //update
            role = daoContext.getRoleDao().find(role.getId());
        }
        role.setStartDate(api.getStartDate()==null ? role.getStartDate() : api.getStartDate());
        role.setFunction(api.getFunction()==null ? role.getFunction() : api.getFunction());
        role.setEndDate(api.getEndDate()==null ? role.getEndDate() : api.getEndDate());
        role.setUser(api.getUser()==0 ? role.getUser() : daoContext.getUserDao().find(api.getUser()));
        role.setCompany(api.getCompany()==0 ? role.getCompany() : daoContext.getCompanyDao().find(api.getCompany()));
        //role permissions
        return role;
    }

    @Override
    public ApiRole convertToApiModel(Role role) {
        ApiRole apiRole = new ApiRole();
        apiRole.setId(role.getId());
        apiRole.setCompany(role.getCompany().getId());
        apiRole.setUser(role.getUser().getId());
        apiRole.setFunction(role.getFunction());
        apiRole.setStartDate(role.getStartDate());
        apiRole.setEndDate(role.getEndDate());
        UriComponentsBuilder bldr = ServletUriComponentsBuilder.fromCurrentRequest();
        apiRole.setUrl(bldr.path(rootPath+"{id}").buildAndExpand(role.getId()).toUriString());
        return apiRole;
    }
}
