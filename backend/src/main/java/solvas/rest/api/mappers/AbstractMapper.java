package solvas.rest.api.mappers;

import solvas.models.Model;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;

/**
 * Class parameter T : class of the domain model class.
 * Class parameter E : class of the api model class.
 * Created by steve on 11/03/2017.
 */
public abstract class AbstractMapper<T extends Model,E> {

    protected final RoleDao roleDao;
    protected final CompanyDao companyDao;
    protected final UserDao userDao;
    protected final VehicleDao vehicleDao;
    protected final VehicleTypeDao vehicleTypeDao;
    protected final FleetSubscriptionDao fleetSubscriptionDao;
    protected final FleetDao fleetDao;
    protected final SubFleetDao subFleetDao;

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
    public AbstractMapper(RoleDao roleDao, CompanyDao companyDao, UserDao userDao, VehicleDao vehicleDao, VehicleTypeDao vehicleTypeDao, FleetSubscriptionDao fleetSubscriptionDao, FleetDao fleetDao, SubFleetDao subFleetDao) {
        this.roleDao = roleDao;
        this.companyDao = companyDao;
        this.userDao = userDao;
        this.vehicleDao = vehicleDao;
        this.vehicleTypeDao = vehicleTypeDao;
        this.fleetSubscriptionDao = fleetSubscriptionDao;
        this.fleetDao = fleetDao;
        this.subFleetDao = subFleetDao;
    }


    /**
     * TODO document
     * @param api
     * @return
     */
    public abstract T convertToModel(E api);

    /**
     * TODO document
     * @param model
     * @return
     */
    public abstract E convertToApiModel(T model);


}
