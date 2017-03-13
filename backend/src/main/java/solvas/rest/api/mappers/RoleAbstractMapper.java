package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.Role;
import solvas.persistence.DaoContext;
import solvas.rest.api.models.ApiRole;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class RoleAbstractMapper extends AbstractMapper<Role,ApiRole> {

    private String rootPath="/roles/";
    /**
     * TODO document
     *
     * @param daoContext
     */
    public RoleAbstractMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Role convertToModel(ApiRole api) {
        Role role = new Role();
        role.setId(api.getId());
        if (role.getId()!=0) {
            //update
            role = daoContext.getRoleDao().find(role.getId());
            if (role==null){
                role=new Role();
            }
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
        apiRole.setUrl(rootPath+apiRole.getId());
        apiRole.setCompany(role.getCompany().getId());
        apiRole.setUser(role.getUser().getId());
        apiRole.setFunction(role.getFunction());
        apiRole.setStartDate(role.getStartDate());
        apiRole.setEndDate(role.getEndDate());
        return apiRole;
    }
}
