package solvas.persistence.api.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Beans for use during testing.
 *
 * @author Karel Vandenbussche
 * @author Niko Strijbol
 */
@Configuration
public class TestConfig {

    Comparator<Resource> migrationComparator =  new Comparator<Resource>() {
        @Override
        public int compare(Resource o1, Resource o2) {
            String o1Name = o1.getFilename().replaceAll("[^_0-9]*", "");
            String o2Name = o2.getFilename().replaceAll("[^_0-9]*", "");
            int o1Version = Integer.valueOf(o1Name.split("_")[0]);
            int o1SubVersion = Integer.valueOf(o1Name.split("_")[1]);
            int o2Version = Integer.valueOf(o2Name.split("_")[0]);
            int o2SubVersion = Integer.valueOf(o2Name.split("_")[1]);


            if (o1Version == o2Version){
                return Integer.compare(o1SubVersion,o2SubVersion);
            } else return Integer.compare(o1Version,o2Version);
        }
    };



    /**
     * Since triggers don't work in the in-memory database, make a custom data source, containing
     * only the migrations, not the triggers.
     *
     * @return The bean.
     */
    @Bean
    public DataSource getDataSource() throws IOException {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //dataSource.setDriverClassName("org.h2.Driver");
        //dataSource.setUrl("jdbc:h2:mem:mydb;MODE=PostgreSQL;");

         dataSource.setDriverClassName("org.hsqldb.jdbc.JDBCDriver"); //move to properties file
         dataSource.setUrl("jdbc:hsqldb:mem:mydb;sql.syntax_pgs=true");

        Resource[] original = loadOriginalResources();
        Resource[] overwritten = loadOverwrittenResources();


        Arrays.sort(original,migrationComparator);

        Arrays.sort(overwritten,migrationComparator);

        int i=0;
        for(Resource resource: overwritten){
            if (i>=original.length) {
                throw new IllegalStateException();
            }
            while (i<original.length) {
                if (resource.getFilename().equals(original[i].getFilename())) {
                    original[i]=resource;
                    i++;
                    break;
                }
                i++;
            }
        }

        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(original);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);


        //Resource initSchema = new ClassPathResource("script/schema.sql");
        //DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        //DatabasePopulatorUtils.execute(databasePopulator, dataSource);



        return dataSource;





    }


    private Resource[] loadOriginalResources() throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader())
                .getResources("classpath:/db/migration/*.sql");
    }


    private Resource[] loadOverwrittenResources() throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader())
                .getResources("classpath:/override/db/migration/*.sql");
    }
}