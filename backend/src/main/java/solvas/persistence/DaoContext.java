package solvas.persistence;

import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;

/**
 * This class ressembles a context/environment wherein the DAO's operate.
 * This class groups all the DAO's that belong to a specific context.
 * A context can one see as the environment where the data is stored.
 * Examples of contexts: postgres DB, MySql DB, CSV files, etc...
 *
 * Created by domien on 12/03/2017.
 */
public interface DaoContext {

    CompanyDao getCompanyDao();

    FleetDao getFleetDao();

    FleetSubscriptionDao getFleetSubscriptionDao();

    RoleDao getRoleDao();

    SubFleetDao getSubFleetDao();

    UserDao getUserDao();

    VehicleDao getVehicleDao();

    VehicleTypeDao getVehicleTypeDao();
}
