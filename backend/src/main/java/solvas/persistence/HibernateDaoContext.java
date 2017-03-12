package solvas.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
@Repository
public class HibernateDaoContext implements DaoContext {

    @Autowired
    public HibernateDaoContext(CompanyDao companyDao, FleetDao fleetDao, FleetSubscriptionDao fleetSubscriptionDao, RoleDao roleDao, SubFleetDao subFleetDao, UserDao userDao, VehicleDao vehicleDao, VehicleTypeDao vehicleTypeDao) {
        this.companyDao = companyDao;
        this.fleetDao = fleetDao;
        this.fleetSubscriptionDao = fleetSubscriptionDao;
        this.roleDao = roleDao;
        this.subFleetDao = subFleetDao;
        this.userDao = userDao;
        this.vehicleDao = vehicleDao;
        this.vehicleTypeDao = vehicleTypeDao;
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
}
