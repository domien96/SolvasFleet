package dao;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import solvas.models.Vehicle;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.company.HibernateCompanyDao;
import solvas.persistence.role.HibernateRoleDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.user.HibernateUserDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.HibernateVehicleDao;
import solvas.persistence.vehicle.VehicleDao;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Hibernate configuration class.
 *
 * Provides various Spring beans to use.
 *
 * @author Niko Strijbol
 */
@Configuration
public class HibernateTestConfig {


    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("db/migration/V2__milestone1.sql").addScript("schema.sql").build();
/*
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:sample;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");
        //dataSource.setUrl("jdbc:h2:~/db/database.db;DB_CLOSE_ON_EXIT=TRUE;INIT=create schema IF NOT EXISTS generic;");
        dataSource.setUsername("vop");
        dataSource.setPassword("vop");


        return dataSource;*/
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

}

