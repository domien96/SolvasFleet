package solvas.rest.api.mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.models.Role;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;
import solvas.rest.api.models.ApiRole;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class RoleMapping extends Mapping<Role,ApiRole> {

    public RoleMapping(RoleDao roleDao, CompanyDao companyDao, UserDao userDao, VehicleDao vehicleDao
            , VehicleTypeDao vehicleTypeDao, FleetSubscriptionDao fleetSubscriptionDao, FleetDao fleetDao, SubFleetDao subFleetDao) {
        super(roleDao, companyDao, userDao, vehicleDao, vehicleTypeDao, fleetSubscriptionDao, fleetDao, subFleetDao);
    }

    @Override
    public Role convertToModel(ApiRole api) {
        Role role = new Role();
        role.setId(api.getId());
        if (role.getId()!=0) {
            //update
            role = roleDao.find(role.getId());
            if (role==null){
                role=new Role();
            }
        }
        role.setStartDate(api.getStartDate()==null ? role.getStartDate() : api.getStartDate());
        role.setFunction(api.getFunction()==null ? role.getFunction() : api.getFunction());
        role.setEndDate(api.getEndDate()==null ? role.getEndDate() : api.getEndDate());
        role.setUser(api.getUser()==0 ? role.getUser() : userDao.find(api.getUser()));
        role.setCompany(api.getCompany()==0 ? role.getCompany() : companyDao.find(api.getCompany()));
        //role permissions
        role.setUpdatedAt(null);
        role.setCreatedAt(role.getCreatedAt());
        return role;
    }

    @Override
    public ApiRole convertToApiModel(Role role) {
        ApiRole apiRole = new ApiRole();
        apiRole.setId(role.getId());
        apiRole.setUrl(role.getUrl());
        apiRole.setCompany(role.getCompany().getId());
        apiRole.setUser(role.getUser().getId());
        apiRole.setFunction(role.getFunction());
        apiRole.setStartDate(role.getStartDate());
        apiRole.setEndDate(role.getEndDate());
        return null;
    }
}
