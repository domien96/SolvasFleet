package solvas.rest.api.mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.models.User;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;
import solvas.rest.api.models.ApiUser;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class UserMapping extends Mapping<User,ApiUser> {

    @Autowired
    public UserMapping(RoleDao roleDao, CompanyDao companyDao, UserDao userDao, VehicleDao vehicleDao, VehicleTypeDao vehicleTypeDao, FleetSubscriptionDao fleetSubscriptionDao) {
        super(roleDao, companyDao, userDao, vehicleDao, vehicleTypeDao, fleetSubscriptionDao);
    }

    @Override
    public User convertToModel(ApiUser apiUser) {
        User user = new User();
        user.setId(apiUser.getId());
        user.setFirstName(apiUser.getFirstName());
        user.setLastName(apiUser.getLastName());
        user.setEmail(apiUser.getEmail());
        user.setPassword(apiUser.getPassword());
        user.setUpdatedAt(apiUser.getUpdatedAt());
        user.setCreatedAt(apiUser.getCreatedAt());
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
        return apiUser;
    }
}
