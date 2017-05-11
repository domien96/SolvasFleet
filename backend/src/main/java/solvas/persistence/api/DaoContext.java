package solvas.persistence.api;

import solvas.persistence.api.dao.*;
import solvas.service.models.Model;

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
     * Get companyDao for this context
     * @return A CompanyDao
     */
    CompanyDao getCompanyDao();

    /**
     * Get fleetDao for this context
     * @return A fleetyDao
     */
    FleetDao getFleetDao();

    /**
     * Get fleetSubscriptionDao for this context
     * @return A FleetSubscriptionDao
     */
    FleetSubscriptionDao getFleetSubscriptionDao();

    /**
     * Get roleDao for this context
     * @return A RoleDao
     */
    RoleDao getRoleDao();

    /**
     * Get userDao for this context
     * @return A UserDao
     */
    UserDao getUserDao();

    /**
     * Get vehicleDao for this context
     * @return A VehcielDao
     */
    VehicleDao getVehicleDao();

    /**
     * Get vehicleTypeDao for this context
     * @return A VehicleTypeDao
     */
    VehicleTypeDao getVehicleTypeDao();

    /**
     * Get InsuranceTypeDao for this context
     * @return A InsuranceTypeDao
     */
    InsuranceTypeDao getInsuranceTypeDao();


    /**
     * Get ContractDao for this context
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

    /**
     * Get InvoiceDao for this context
     * @return A InvoiceDao
     */
    InvoiceDao getInvoiceDao();

    /**
     * Get TaxDao for this content
     * @return A InvoiceDao
     */
    TaxDao getTaxDao();


    /**
     * Get RevisionDao for this content
     * @return A InvoiceDao
     */
    RevisionDao getRevisionDao();



}
