package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
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

import javax.sql.DataSource;

/**
 * Config for testing
 */
@Configuration
public class HibernateTestConfig {


    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("db/migration/V1__milestone1.sql").addScript("db/migration/V2_1__n-m_relations.sql").addScript("schema.sql").build();
    }





    @Bean
    public CompanyDao companyDao()
    {
        return new HibernateCompanyDao();
    }

    @Bean
    public RoleDao roleDao()
    {
        return new HibernateRoleDao();
    }

    @Bean
    public UserDao userDao()
    {
        return new HibernateUserDao();
    }

    @Bean
    public VehicleDao vehicleDao()
    {
        return new HibernateVehicleDao();
    }

    @Bean
    public FleetDao fleetDao() { return new HibernateFleetDao();}

    @Bean
    public SubFleetDao subFleetDao() { return new HibernateSubFleetDao(); }

    @Bean
    public FleetSubscriptionDao fleetSubscriptionDao() { return new HibernateFleetSubscriptionDao(); }

}

