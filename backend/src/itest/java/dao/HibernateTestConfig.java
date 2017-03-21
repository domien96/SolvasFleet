package dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Config for testing
 */
@Configuration
public class HibernateTestConfig {

    @Bean
    @Profile("test")
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("db/migration/V1_0__milestone1.sql")
                .addScript("db/migration/V1_1__n-m_relations.sql")
                .addScript("schema.sql")
                .build();
    }
}