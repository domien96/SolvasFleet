package solvas.models;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * Common code for testing the API model's validations.
 *
 * @author Niko Strijbol
 */
public class ValidationTest {

    protected Validator validator;

    /**
     * Initiate the validator.
     */
    @Before
    public void setUp() {
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        factoryBean.afterPropertiesSet();
        validator = factoryBean.getValidator();
    }
}