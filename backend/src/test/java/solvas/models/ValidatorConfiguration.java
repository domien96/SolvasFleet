package solvas.models;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Base class for the Spring configuration, used by the tests.
 *
 * @author Niko Strijbol
 */
public class ValidatorConfiguration {

    /**
     * @return The validator factory.
     */
    static LocalValidatorFactoryBean createFactory() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    /**
     * @return The validator factory.
     */
    @Bean
    public LocalValidatorFactoryBean validator() {
        return createFactory();
    }
}