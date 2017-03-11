package solvas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
 
@Configuration
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@ComponentScan(value = {"solvas"})
public class Application {
 
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
        return new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }
}