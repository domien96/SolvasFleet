package solvas.persistence.api;

import solvas.persistence.api.dao.*;

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
     * Get companyDao for this content
     * @return A CompanyDao
     */
    CompanyDao getCompanyDao();

    /**
     * Get fleetDao for this content
     * @return A fleetyDao
     */
    FleetDao getFleetDao();

    /**
     * Get fleetSubscriptionDao for this content
     * @return A FleetSubscriptionDao
     */
    FleetSubscriptionDao getFleetSubscriptionDao();

    /**
     * Get roleDao for this content
     * @return A RoleDao
     */
    RoleDao getRoleDao();

    /**
     * Get subFleetDao for this content
     * @return A SubFleetDao
     */
    SubFleetDao getSubFleetDao();

    /**
     * Get userDao for this content
     * @return A UserDao
     */
    UserDao getUserDao();

    /**
     * Get vehicleDao for this content
     * @return A VehcielDao
     */
    VehicleDao getVehicleDao();

    /**
     * Get vehicleTypeDao for this content
     * @return A VehicleTypeDao
     */
    VehicleTypeDao getVehicleTypeDao();

    /**
     * Get InsuranceTypeDao for this content
     * @return A InsuranceTypeDao
     */
    InsuranceTypeDao getInsuranceTypeDao();


    /**
     * Get ContractDao for this content
     * @return A VehicleTypeDao
     */
    ContractDao getContractDao();

    /**
     * Get FunctionDao for this content
     * @return A FunctionDao
     */
    FunctionDao getFunctionDao();

    /**
     * Get PermissionDao for this content
     * @return A PermissionDao
     */
    PermissionDao getPermissionDao();
}
