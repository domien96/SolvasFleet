package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.Company;
import solvas.models.User;
import solvas.persistence.DaoContext;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;
import solvas.rest.api.models.ApiUser;

/**
 * Mapper between User and ApiUser
 */
@Component
public class UserAbstractMapper extends AbstractMapper<User,ApiUser> {

    private String rootPath="/users/";
    /**
     * Create UserMapper
     *
     * @param daoContext The DAO context
     */
    public UserAbstractMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public User convertToModel(ApiUser apiUser) {
        User user = new User();
        user.setId(apiUser.getId());
        if (user.getId()!=0) {
            //update
            user = daoContext.getUserDao().find(user.getId());
            if (user==null){
                user=new User();
            }
        }

        user.setFirstName(apiUser.getFirstName());
        user.setLastName(apiUser.getLastName());
        user.setEmail(apiUser.getEmail());
        user.setPassword(apiUser.getPassword());
        user.setUpdatedAt(apiUser.getUpdatedAt());
        user.setCreatedAt(user.getCreatedAt());
        return user;
    }

    @Override
    public ApiUser convertToApiModel(User user) {
        ApiUser apiUser = new ApiUser();
        apiUser.setId(user.getId());
        apiUser.setFirstName(user.getFirstName());
        apiUser.setLastName(user.getLastName());
        apiUser.setEmail(user.getEmail());
        apiUser.setPassword(user.getPassword());
        apiUser.setUpdatedAt(user.getUpdatedAt());
        apiUser.setCreatedAt(user.getCreatedAt());
        apiUser.setUrl(rootPath+apiUser.getId());
        return apiUser;
    }
}
