package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.Role;
import solvas.persistence.DaoContext;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.rest.api.models.ApiRole;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class RoleMapper extends AbstractMapper<Role,ApiRole> {

    protected final DaoContext daoContext;


    /**
     * TODO document
     *
     * @param daoContext
     */
    public RoleMapper(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    @Override
    public Role convertToModel(ApiRole api) throws EntityNotFoundException, FieldNotFoundException {
        Role role = new Role();
        role.setId(api.getId());
        if (role.getId()!=0) {
            //update
            role = daoContext.getRoleDao().find(role.getId());
        }

        copyAttributes(role, api, "startDate", "function", "endDate", "createdAt");

        role.setUser(api.getUser()==0 ? role.getUser() : daoContext.getUserDao().find(api.getUser()));
        role.setCompany(api.getCompany()==0 ? role.getCompany() : daoContext.getCompanyDao().find(api.getCompany()));
        return role;
    }

    @Override
    public ApiRole convertToApiModel(Role role) throws FieldNotFoundException {
        ApiRole apiRole = new ApiRole();
        copyAttributes(apiRole, role, "id", "function", "startDate", "endDate");
        apiRole.setUrl(role.getUrl());
        apiRole.setCompany(role.getCompany().getId());
        apiRole.setUser(role.getUser().getId());
        return apiRole;
    }
}
