package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.User;
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
 * Created by steve on 11/03/2017.
 */
@Component
public class UserAbstractMapper extends AbstractMapper<User,ApiUser> {

    /**
     * TODO document
     * @param roleDao
     * @param companyDao
     * @param userDao
     * @param vehicleDao
     * @param vehicleTypeDao
     * @param fleetSubscriptionDao
     * @param fleetDao
     * @param subFleetDao
     */
    public UserAbstractMapper(RoleDao roleDao, CompanyDao companyDao, UserDao userDao, VehicleDao vehicleDao
            , VehicleTypeDao vehicleTypeDao, FleetSubscriptionDao fleetSubscriptionDao, FleetDao fleetDao, SubFleetDao subFleetDao) {
        super(roleDao, companyDao, userDao, vehicleDao, vehicleTypeDao, fleetSubscriptionDao, fleetDao, subFleetDao);
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
