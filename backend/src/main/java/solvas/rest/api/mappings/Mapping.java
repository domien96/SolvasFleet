package solvas.rest.api.mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import solvas.models.Model;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;

/**
 * Created by steve on 11/03/2017.
 */
public abstract class Mapping<T extends Model,E> {

    protected final RoleDao roleDao;
    protected final CompanyDao companyDao;
    protected final UserDao userDao;
    protected final VehicleDao vehicleDao;
    protected final VehicleTypeDao vehicleTypeDao;
    protected final FleetSubscriptionDao fleetSubscriptionDao;


    public Mapping(RoleDao roleDao, CompanyDao companyDao, UserDao userDao, VehicleDao vehicleDao, VehicleTypeDao vehicleTypeDao, FleetSubscriptionDao fleetSubscriptionDao) {
        this.roleDao = roleDao;
        this.companyDao = companyDao;
        this.userDao = userDao;
        this.vehicleDao = vehicleDao;
        this.vehicleTypeDao = vehicleTypeDao;
        this.fleetSubscriptionDao = fleetSubscriptionDao;
    }


    public abstract T convertToModel(E api);

    public abstract E convertToApiModel(T model);


}
