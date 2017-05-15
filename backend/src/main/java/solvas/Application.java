package solvas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import solvas.rest.utils.PagedResult;


/**
 * The SolvasFleet application bootstrap
 */
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan(value = {"solvas"})
@SpringBootApplication
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
     * We need a custimozid version of the object mapper.
     *
     * @return The general object mapper for the application.
     */
    @Bean
    public ObjectMapper jacksonObjectMapper() {
        return defaultObjectMapper();
    }

    /**
     * Creates an {@link ObjectMapper} that has been configured for this application.
     *
     * @return The object mapper.
     */
    public static ObjectMapper defaultObjectMapper() {
        return new ObjectMapper()
                .findAndRegisterModules()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
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

    @Bean
    public SpringDataWebConfiguration dataWebConfiguration() {
        return new SpringDataWebConfiguration() {
            @Override
            public PageableHandlerMethodArgumentResolver pageableResolver() {
                PageableHandlerMethodArgumentResolver resolver = super.pageableResolver();
                resolver.setSizeParameterName(PagedResult.PAGINATION_QUERY_SIZE);
                resolver.setPageParameterName(PagedResult.PAGINATION_QUERY_PAGE);
                return resolver;
            }
        };
    }
}