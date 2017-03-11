package solvas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import solvas.models.Company;
import solvas.rest.deserializer.CompanyDeserializer;
import solvas.rest.serializer.CompanySerializer;

/**
 * The SolvasFleet application bootstrap
 */
@Configuration
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@ComponentScan(value = {"solvas"})
public class Application {

    /**
     * Start the SolvasFleet application.
     *
     * @param args The args passed in the command line
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Automatically let Jackson convert CamelCase attributes to snake_case.
     *
     * @return The Jackson object mapper.
     */
    @Bean
    public ObjectMapper jacksonObjectMapper() {
        CompanyDeserializer companyDeserializer=new CompanyDeserializer();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Company.class,new CompanySerializer());
        module.addDeserializer(Company.class,companyDeserializer);




        ObjectMapper mapper = new ObjectMapper()
                .findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(module);

        companyDeserializer.setObjectMapper(mapper);


        return mapper;
    }

    @Bean
    @Profile({"debug", "clean"})
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                super.addCorsMappings(registry);
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }
        };
    }
}