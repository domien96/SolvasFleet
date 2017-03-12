package solvas.rest.api.mappings;

import org.springframework.beans.factory.annotation.Autowired;
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
@Component
public abstract class Mapping<T extends Model,E> {

    @Autowired
    protected RoleDao roleDao;
    @Autowired
    protected CompanyDao companyDao;
    @Autowired
    protected UserDao userDao;
    @Autowired
    protected VehicleDao vehicleDao;
    @Autowired
    protected VehicleTypeDao vehicleTypeDao;
    @Autowired
    protected FleetSubscriptionDao fleetSubscriptionDao;


    public abstract T convertToModel(E api);

    public abstract E convertToApiModel(T model);


}
