package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import solvas.persistence.api.dao.*;
import solvas.persistence.hibernate.dao.*;

import javax.sql.DataSource;

/**
 * Config for testing
 */
@Configuration
public class HibernateTestConfig {


    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("db/migration/V1_0__milestone1.sql").addScript("db/migration/V1_1__n-m_relations.sql").addScript("schema.sql").build();
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

