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

    /**
     * Get companyDao fox this content
     * @return A CompanyDao
     */
    CompanyDao getCompanyDao();

    /**
     * Get fleetDao fox this content
     * @return A fleetyDao
     */
    FleetDao getFleetDao();

    /**
     * Get fleetSubscriptionDao fox this content
     * @return A FleetSubscriptionDao
     */
    FleetSubscriptionDao getFleetSubscriptionDao();

    /**
     * Get roleDao fox this content
     * @return A RoleDao
     */
    RoleDao getRoleDao();

    /**
     * Get subFleetDao fox this content
     * @return A SubFleetDao
     */
    SubFleetDao getSubFleetDao();

    /**
     * Get userDao fox this content
     * @return A UserDao
     */
    UserDao getUserDao();

    /**
     * Get vehicleDao fox this content
     * @return A VehcielDao
     */
    VehicleDao getVehicleDao();

    /**
     * Get vehicleTypeDao fox this content
     * @return A VehicleTypeDao
     */
    VehicleTypeDao getVehicleTypeDao();
}
