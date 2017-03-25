package solvas.persistence.api.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

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
     */
    @Bean
    public DataSource getDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("db/migration/V1_0__milestone1.sql")
                .addScript("db/migration/V1_1__n-m_relations.sql")
                .addScript("schema.sql")
                .build();
    }
}