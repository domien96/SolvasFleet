package solvas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
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
import solvas.rest.utils.validators.DaoContextAwareConstraintValidatorFactory;

import javax.validation.Validation;
import javax.validation.Validator;


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
     * Automatically let Jackson convert CamelCase attributes to snake_case.
     *
     * @return The Jackson object mapper.
     */
    @Bean
    public ObjectMapper jacksonObjectMapper() {

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

    /**
     * Configure validators
     * @param daoContextAwareConstraintValidatorFactory Factory to build validators that query the database
     * @return Validator
     */
    @Bean
    @Autowired
    public Validator validator(DaoContextAwareConstraintValidatorFactory daoContextAwareConstraintValidatorFactory) {
        return Validation.byDefaultProvider()
                .configure()
                .constraintValidatorFactory( daoContextAwareConstraintValidatorFactory )
                .buildValidatorFactory().getValidator();
    }
}