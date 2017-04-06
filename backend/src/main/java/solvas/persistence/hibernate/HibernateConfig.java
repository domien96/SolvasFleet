package solvas.persistence.hibernate;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Hibernate configuration class.
 *
 * Provides various Spring beans to use.
 *
 * @author Niko Strijbol
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:hibernate.properties"})
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

    /**
     * Get datasource bean that contains database connection info
     * @return the datasource
     */
    @Bean
    @Profile({"default", "debug", "clean"})
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
     * @return Hibernate properties from a file.
     */
    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put(AvailableSettings.DIALECT, env.getRequiredProperty("hibernate.dialect"));
        properties.put(AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS, env.getRequiredProperty("hibernate.current.session.context.class"));
        properties.put(AvailableSettings.STATEMENT_BATCH_SIZE, env.getRequiredProperty("hibernate.batch.size"));
        properties.put(AvailableSettings.ENABLE_LAZY_LOAD_NO_TRANS, true);
        return properties;
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

    /**
     *
     * @param dataSource The datasource to be used by hibernate
     * @return Factory to create entity managers
     * @throws IOException Couldn't load the hibernate configuration/mapping files
     */
    @Bean
    public EntityManagerFactory entityManagerFactory(DataSource dataSource) throws IOException {
        LocalContainerEntityManagerFactoryBean  entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource);
        entityManager.setJpaProperties(getHibernateProperties());
        Resource[] resources = loadResources();
        String[] paths = new String[resources.length];
        for (int i = 0; i < resources.length; i++) {
            paths[i] = resources[i].getFilename();
        }
        String[] pr = Arrays.stream(paths).map(p -> "/mappings/" + p).toArray(String[]::new);
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setMappingResources(pr);
        entityManager.afterPropertiesSet();
        return entityManager.getObject();
    }
}