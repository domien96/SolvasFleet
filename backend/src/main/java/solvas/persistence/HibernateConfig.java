package solvas.persistence;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Autowired
    private Environment env;

    @Autowired
    private ResourceLoader rl;

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
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getDataSource());
        sessionFactory.setHibernateProperties(getHibernateProperties());
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
        properties.put(AvailableSettings.SHOW_SQL, env.getRequiredProperty("hibernate.show_sql"));
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty("hibernate.current.session.context.class"));
        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch.size"));
        properties.put(AvailableSettings.HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2ddl.auto"));
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
}