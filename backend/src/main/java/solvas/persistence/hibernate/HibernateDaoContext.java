package solvas.persistence.hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.*;

/**
 * Uses lazy creation.
 * Created by domien on 12/03/2017.
 */
@Service
public class HibernateDaoContext implements DaoContext {

    /**
     * Create DaoContext with Dao's
     * @param companyDao
     * @param fleetDao
     * @param fleetSubscriptionDao
     * @param roleDao
     * @param subFleetDao
     * @param userDao
     * @param vehicleDao
     * @param vehicleTypeDao
     * @param contractDao
     * @param insuranceTypeDao
     * @param functionDao
     * @param permissionDao
     * @param invoiceDao
     */
    @Autowired
    public HibernateDaoContext(CompanyDao companyDao, FleetDao fleetDao, FleetSubscriptionDao fleetSubscriptionDao,
                               RoleDao roleDao, SubFleetDao subFleetDao, UserDao userDao, VehicleDao vehicleDao,
                               VehicleTypeDao vehicleTypeDao,
                               ContractDao contractDao, InsuranceTypeDao insuranceTypeDao,
                               FunctionDao functionDao, PermissionDao permissionDao,InvoiceDao invoiceDao) {
        this.companyDao = companyDao;
        this.fleetDao = fleetDao;
        this.fleetSubscriptionDao = fleetSubscriptionDao;
        this.roleDao = roleDao;
        this.subFleetDao = subFleetDao;
        this.userDao = userDao;
        this.vehicleDao = vehicleDao;
        this.vehicleTypeDao = vehicleTypeDao;
        this.contractDao = contractDao;
        this.insuranceTypeDao=insuranceTypeDao;
        this.functionDao = functionDao;
        this.permissionDao = permissionDao;
        this.invoiceDao=invoiceDao;
    }

    private CompanyDao companyDao;

    @Override
    public CompanyDao getCompanyDao() {
        return companyDao;
    }

    private FleetDao fleetDao;

    @Override
    public FleetDao getFleetDao() {
        return fleetDao;
    }

    private FleetSubscriptionDao fleetSubscriptionDao;

    @Override
    public FleetSubscriptionDao getFleetSubscriptionDao() {
        return fleetSubscriptionDao;
    }

    private RoleDao roleDao;

    @Override
    public RoleDao getRoleDao() {
        return roleDao;
    }

    private SubFleetDao subFleetDao;

    @Override
    public SubFleetDao getSubFleetDao() {
        return subFleetDao;
    }

    private UserDao userDao;

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    private VehicleDao vehicleDao;

    @Override
    public VehicleDao getVehicleDao() {
        return vehicleDao;
    }

    private VehicleTypeDao vehicleTypeDao;

    @Override
    public VehicleTypeDao getVehicleTypeDao() {
        return vehicleTypeDao;
    }

    @Override
    public InsuranceTypeDao getInsuranceTypeDao() {
        return insuranceTypeDao;
    }



    private InsuranceTypeDao insuranceTypeDao;

    private ContractDao contractDao;

    @Override
    public ContractDao getContractDao() {
        return contractDao;
    }

    private FunctionDao functionDao;
    @Override
    public FunctionDao getFunctionDao() {
        return functionDao;
    }

    private PermissionDao permissionDao;

    @Override
    public PermissionDao getPermissionDao() {
        return permissionDao;
    }

    private InvoiceDao invoiceDao;

    @Override
    public InvoiceDao getInvoiceDao() {
        return invoiceDao;
    }
}
