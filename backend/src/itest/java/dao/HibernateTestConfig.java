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

    private final Environment env;
    private final ResourceLoader rl;

    /**
     * Injection constructor.
     *
     *
     * @param env The environment.
     * @param resourceLoader The resource loader.
     */
    @Autowired
    public HibernateTestConfig(Environment env, ResourceLoader resourceLoader) {
        this.env = env;
        this.rl = resourceLoader;
    }

    private Resource[] loadResources() throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(rl)
                .getResources("classpath:/mappings/*.hbm.xml");
    }


    /**
     * The session factory bean. Spring manages everything for us.
     *
     * @return The factory.
     */
    @Bean
    @Profile("default,test")
    public LocalSessionFactoryBean sessionFactory() {
        return createSessionFactory(getHibernateProperties());
    }





    /**
     * Create a session factory bean, given some properties.
     *
     * @param hibernateProperties The hibernate properties to use.
     *
     * @return The bean if successful, otherwise a {@link BeanCreationException} is thrown.
     */
    @Bean
    public LocalSessionFactoryBean createSessionFactory(Properties hibernateProperties) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setHibernateProperties(hibernateProperties);
        try {
            // Automatically load mappings.
            sessionFactory.setMappingLocations(loadResources());
            return sessionFactory;
        } catch (IOException e) {
            throw new BeanCreationException("sessionFactory", e);
        }
    }

    /**
     * @return Hibernate properties from a file.
     */
    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, env.getRequiredProperty("hibernate.dialect"));
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty("hibernate.current.session.context.class"));
        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch.size"));

        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        return properties;
    }

    /**
     * Transaction bean.
     *
     * @param sessionFactory Injected session factory.
     *
     * @return The transaction manager.
     */
    @Bean
    public HibernateTransactionManager transactionManager(LocalSessionFactoryBean sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory.getObject());
        return txManager;
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

