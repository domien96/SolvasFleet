package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
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
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.rest.api.models.ApiUser;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class UserMapper extends AbstractMapper<User,ApiUser> {
    private DaoContext daoContext;


    /**
     * TODO document
     *
     * @param daoContext
     */
    public UserMapper(DaoContext daoContext) {
        this.daoContext = daoContext;
    }

    @Override
    public User convertToModel(ApiUser apiUser) throws FieldNotFoundException {
        User user = new User();

        copyAttributes(user, apiUser, "id", "firstName", "lastName", "email", "password", "createdAt", "updatedAt");
        return user;
    }

    @Override
    public ApiUser convertToApiModel(User user) throws FieldNotFoundException {
        ApiUser apiUser = new ApiUser();

        copyAttributes(apiUser, user, "id", "firstName", "lastName", "email", "password", "createdAt", "updatedAt");

        return apiUser;
    }
}
