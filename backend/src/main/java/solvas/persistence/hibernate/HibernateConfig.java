package solvas.persistence.hibernate;

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
@EnableTransactionManagement
@PropertySource(value = {"hibernate.properties"})
public class HibernateConfig {

    private final Environment env;
    private final ResourceLoader rl;

    /**
     * Injection constructor.
     *
     * @param env The environment.
     * @param resourceLoader The resource loader.
     */
    @Autowired
    public HibernateConfig(Environment env, ResourceLoader resourceLoader) {
        this.env = env;
        this.rl = resourceLoader;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("datasource.driver"));
        dataSource.setUrl(env.getProperty("datasource.url"));
        dataSource.setUsername(env.getProperty("datasource.username"));
        dataSource.setPassword(env.getProperty("datasource.password"));
        return dataSource;
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
     * The session factory bean. Spring manages everything for us.
     *
     * @return The factory.
     */
    @Bean
    @Profile({"debug", "clean"})
    public LocalSessionFactoryBean sessionDebugFactory() {
        Properties hibernateProperties = getHibernateProperties();
        hibernateProperties.put(AvailableSettings.SHOW_SQL, true);
        return createSessionFactory(hibernateProperties);
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

    /**
     * Modify the flyway strategy to clean the database before migrating. Do not remove the clean profile annotation,
     * or the production database could be gone!
     *
     * @return The strategy for migrating.
     */
    @Bean
    @Profile("clean")
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}