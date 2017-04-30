package solvas.persistence.api.dao;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

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
public class TestConfig {




    /**
     * Since triggers don't work in the in-memory database, make a custom data source, containing
     * only the migrations, not the triggers.
     *
     * @return The bean.
     *//*
    @Bean
    public DataSource getDataSource() throws IOException {
     }

        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(original);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);


        //Resource initSchema = new ClassPathResource("script/seed_data.sql");
        //DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        //DatabasePopulatorUtils.execute(databasePopulator, dataSource);



        return dataSource;

    }*/


    /**
     * Since triggers don't work in the in-memory database, make a custom data source, containing
     * only the migrations, not the triggers.
     *
     * @return The bean.
     */
    @Bean
    public DataSource getDataSource() throws IOException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        Properties prop = new Properties();
        prop.load(new ClassPathResource("/test.properties").getInputStream());
        dataSource.setDriverClassName(prop.getProperty("datasource.driver"));
        dataSource.setUrl(prop.getProperty("datasource.url"));
        dataSource.setUsername(prop.getProperty("datasource.username"));
        dataSource.setPassword(prop.getProperty("datasource.password"));
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.clean();
        flyway.migrate();

        Resource initSchema = new ClassPathResource("/seed_data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        return dataSource;
    }


}