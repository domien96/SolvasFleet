package solvas.persistence.api.dao;

import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import solvas.service.audit.AuditInterceptor;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * Beans for use during testing.
 *
 * @author Karel Vandenbussche
 * @author Niko Strijbol
 */
@Configuration
@ComponentScan("solvas.rest.utils.validators") //otherwise validator error
@ComponentScan("solvas.persistence.hibernate") //otherwise validator error
@ComponentScan("solvas") // search entire solvas for beans
@SpringBootApplication
public class TestConfig {

    /**
     * Make migrate the migrations to the test db
     *
     * @return The bean.
     */
    @Bean
    public DataSource getDataSource() throws IOException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //Build datasource from properties
        Properties prop = new Properties();
        prop.load(new ClassPathResource("test.properties").getInputStream());
        dataSource.setDriverClassName(prop.getProperty("datasource.driver"));
        dataSource.setUrl(prop.getProperty("datasource.url"));
        dataSource.setUsername(prop.getProperty("datasource.username"));
        dataSource.setPassword(prop.getProperty("datasource.password"));

        //Migrate the migrations
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        flyway.migrate();

        //Seed the db
        Resource initSchema = new ClassPathResource("/seed_data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        return dataSource;
    }


    /**
     * Test config cannot find javax.validation.Validator bean, so we create a new factory
     * @return new Validator
     */
    @Bean
    public javax.validation.Validator localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean();
    }

}