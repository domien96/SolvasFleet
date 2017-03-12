package solvas.persistence;

import solvas.models.Company;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.company.HibernateCompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleet.HibernateFleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.fleetSubscription.HibernateFleetSubscriptionDao;
import solvas.persistence.role.HibernateRoleDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.HibernateSubFleetDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.HibernateUserDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.HibernateVehicleDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.HibernateVehicleTypeDao;
import solvas.persistence.vehicleType.VehicleTypeDao;

/**
 * Uses lazy creation.
 * Created by domien on 12/03/2017.
 */
public class HibernateDaoContext implements DaoContext {

    private CompanyDao companyDao = null;

    @Override
    public CompanyDao getCompanyDao() {
        if (companyDao==null)
            companyDao = new HibernateCompanyDao();
        return companyDao;
    }

    private FleetDao fleetDao = null;

    @Override
    public FleetDao getFleetDao() {
        if (fleetDao==null)
            fleetDao = new HibernateFleetDao();
        return fleetDao;
    }

    private FleetSubscriptionDao fleetSubscriptionDao = null;

    @Override
    public FleetSubscriptionDao getFleetSubscriptionDao() {
        if (fleetSubscriptionDao==null)
            fleetSubscriptionDao = new HibernateFleetSubscriptionDao();
        return fleetSubscriptionDao;
    }

    private RoleDao roleDao = null;

    @Override
    public RoleDao getRoleDao() {
        if (roleDao==null)
            roleDao = new HibernateRoleDao();
        return roleDao;
    }

    private SubFleetDao subFleetDao = null;

    @Override
    public SubFleetDao getSubFleetDao() {
        if (subFleetDao==null)
            subFleetDao = new HibernateSubFleetDao();
        return subFleetDao;
    }

    private UserDao userDao = null;

    @Override
    public UserDao getUserDao() {
        if (userDao==null)
            userDao = new HibernateUserDao();
        return userDao;
    }

    private VehicleDao vehicleDao = null;

    @Override
    public VehicleDao getVehicleDao() {
        if (vehicleDao==null)
            vehicleDao = new HibernateVehicleDao();
        return vehicleDao;
    }

    private VehicleTypeDao vehicleTypeDao = null;

    @Override
    public VehicleTypeDao getVehicleTypeDao() {
        if (vehicleTypeDao==null)
            vehicleTypeDao = new HibernateVehicleTypeDao();
        return vehicleTypeDao;
    }
}
