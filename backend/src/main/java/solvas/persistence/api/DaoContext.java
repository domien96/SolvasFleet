package solvas.persistence.api;

import solvas.persistence.api.dao.CompanyDao;
import solvas.persistence.api.dao.FleetDao;
import solvas.persistence.api.dao.FleetSubscriptionDao;
import solvas.persistence.api.dao.RoleDao;
import solvas.persistence.api.dao.SubFleetDao;
import solvas.persistence.api.dao.UserDao;
import solvas.persistence.api.dao.VehicleDao;
import solvas.persistence.api.dao.VehicleTypeDao;

/**
 * This class ressembles a context/environment wherein the DAO's operate.
 * This class groups all the DAO's that belong to a specific context.
 * A persistence context can one see as the storage environment where the data is stored.
 * Examples of persistence contexts: postgres DB, MySql DB, CSV files, etc...
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
