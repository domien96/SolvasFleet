package solvas.service.models;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.UserDao;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.models.ApiUser;
import solvas.rest.utils.validators.UniqueEmailForUserValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import java.util.Optional;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test that {@link ApiUser}'s validations work.
 *
 * @author Niko Strijbol
 */
public class ApiUserValidationTest extends ValidationTest {

    @Mock
    private DaoContext context;

    @Mock
    private UserDao userDao;
    /**
     * Setting up the tests
     */
    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        factoryBean.setConstraintValidatorFactory(new ApiUserValidationTest.CustomConstraintValidatorFactory());
        factoryBean.afterPropertiesSet();

        validator = factoryBean.getValidator();

        when(context.getUserDao()).thenReturn(userDao);
    }

    /**
     * Test a valid instance.
     */
    @Test
    public void testValid() {
        ApiUser user = random(ApiUser.class);
        user.setEmail("test@example.com");
        emulateEmailConstraint(user.getEmail(), user, true);
        assertEquals(0, validator.validate(user).size());
    }

    /**
     * Test if the email is validated.
     */
    @Test
    public void testEmail() {
        String emailField = "email";
        ApiUser user = random(ApiUser.class, emailField);

        Set<ConstraintViolation<ApiUser>> v = validator.validate(user);
        assertEquals(1, v.size());
        assertEquals(emailField, v.iterator().next().getPropertyPath().iterator().next().getName());

        user.setEmail("VERY BAD EMAIL");
        emulateEmailConstraint(user.getEmail(), user, true);
        v = validator.validate(user);
        assertEquals(1, v.size());
        assertEquals(emailField, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    /**
     * Test a user without any fields set and test a user with empty fields.
     */
    @Test
    public void testNullsAndEmpty() {
        ApiUser user = new ApiUser();
        validator.validate(user);
        assertEquals(4, validator.validate(user).size()); //4 errors Password not null, lastname,email,firstname
        user.setFirstName("");
        user.setLastName("");
        user.setPassword("");
        assertEquals(3, validator.validate(user).size());
    }

    private void emulateEmailConstraint(String email, ApiUser user, boolean expected) {
        when(context.getUserDao()).thenReturn(userDao);
        Optional<User> optional;
        if (expected) {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setId(user.getId());
            optional = Optional.of(newUser);
        } else {
            optional = Optional.empty();

        }
        when(userDao.getByEmail(email)).thenReturn(optional);
    }


    class CustomConstraintValidatorFactory implements ConstraintValidatorFactory {

        LocalValidatorFactoryBean factoryBean;

        public CustomConstraintValidatorFactory() {
            factoryBean = new LocalValidatorFactoryBean();
            factoryBean.setProviderClass(HibernateValidator.class);
            factoryBean.afterPropertiesSet();
        }

        @Override
        public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
            if (key.equals(UniqueEmailForUserValidator.class)) {
                return (T) new UniqueEmailForUserValidator(context);
            }
            return factoryBean.getConstraintValidatorFactory().getInstance(key);
        }

        @Override
        public void releaseInstance(ConstraintValidator<?, ?> instance) {
            factoryBean.getConstraintValidatorFactory().releaseInstance(instance);
        }
    }
}