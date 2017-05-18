package solvas.service.models;

import org.hibernate.validator.HibernateValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.utils.validators.UniqueVinForVehicleValidator;
import solvas.service.VehicleService;
import solvas.service.models.validators.IsValidVehicleTypeValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Test that {@link ApiVehicle}'s validation works.
 *
 * @author Niko Strijbol
 */
public class ApiVehicleValidationTest extends ValidationTest {

    private static final String VALID_VIN = "JM3KE4CY5F0442856";
    private static final String INVALID_VIN = "JM3KE4CY65655F0442856";

    @Mock
    private DaoContext context;

    @Mock
    private VehicleDao vehicleDao;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private IsValidVehicleTypeValidator isValidVehicleTypeValidator;

    @InjectMocks
    private UniqueVinForVehicleValidator uniqueVinForVehicleValidator;


    /**
     * Setting up the tests
     */
    @Before
    @Override
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setProviderClass(HibernateValidator.class);
        factoryBean.setConstraintValidatorFactory(new ApiVehicleValidationTest.CustomConstraintValidatorFactory());
        factoryBean.afterPropertiesSet();

        validator = factoryBean.getValidator();

        when(context.getVehicleDao()).thenReturn(vehicleDao);
    }

    /**
     * Test a valid instance.
     */
    @Test
    public void testValid() {
        ApiVehicle vehicle = random(ApiVehicle.class);
        vehicle.setVin(VALID_VIN);
        vehicle.setYear(LocalDateTime.of(2014, 1, 1, 0, 0));
        vehicle.setMileage(2000);
        vehicle.setValue(10);
        emulateVinConstraint(vehicle.getVin(), false);
        assertEquals(1, validator.validate(vehicle).size());
    }

    /**
     * Test that the vin number is being validated.
     */
    @Test
    public void testVin() {
        String vinField = "vin";
        ApiVehicle vehicle = random(ApiVehicle.class, vinField);
        vehicle.setYear(LocalDateTime.of(2014, 1, 1, 0, 0));
        vehicle.setMileage(2000);
        vehicle.setValue(10);
        vehicle.setVin(INVALID_VIN);

        when(vehicleService.findAllVehicleTypes()).thenReturn(new ArrayList<String>());

        emulateVinConstraint(vehicle.getVin(), false);
        Set<ConstraintViolation<ApiVehicle>> v = validator.validate(vehicle);
        assertEquals(2, v.size());
        assertEquals("vin", v.iterator().next().getPropertyPath().iterator().next().getName());


        vehicle.setVin("");
        v = validator.validate(vehicle);
        assertEquals(2, v.size());
        assertEquals(vinField, v.iterator().next().getPropertyPath().iterator().next().getName());

        vehicle.setVin(null);
        v = validator.validate(vehicle);
        assertEquals(2, v.size());
        assertEquals(vinField, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    /**
     * Test the numerical fields.
     */
    @Test
    public void testNumbers() {
        ApiVehicle vehicle = random(ApiVehicle.class);
        vehicle.setYear(LocalDateTime.of(1500, 1, 1, 0, 0));
        vehicle.setMileage(-50);
        vehicle.setValue(-99693);
        vehicle.setVin(VALID_VIN);
        emulateVinConstraint(vehicle.getVin(), false);
        assertEquals(4, validator.validate(vehicle).size());
    }

    /**
     * Test empty and null fields.
     */
    @Test
    public void testEmptyAndNull() {
        ApiVehicle vehicle = new ApiVehicle();
        vehicle.setYear(LocalDateTime.of(2000, 1, 1, 0, 0));
        vehicle.setMileage(6565);
        vehicle.setValue(63);

        assertEquals(5, validator.validate(vehicle).size());

        vehicle.setVin(VALID_VIN);
        vehicle.setBrand("");
        vehicle.setModel("");
        vehicle.setType("");
        emulateVinConstraint(vehicle.getVin(), false);
        assertEquals(4, validator.validate(vehicle).size());
    }

    private void emulateVinConstraint(String vin, boolean expected) {
        when(context.getVehicleDao()).thenReturn(vehicleDao);
        Optional<Vehicle> optional;
        if (expected) {
            optional = Optional.of(new Vehicle());
        } else {
            optional = Optional.empty();

        }
        when(vehicleDao.findByChassisNumber(vin)).thenReturn(optional);
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
            if (key.equals(UniqueVinForVehicleValidator.class)) {
                return (T) new UniqueVinForVehicleValidator(context);
            }
            if (key.equals(IsValidVehicleTypeValidator.class)){
                return (T) isValidVehicleTypeValidator;
            }
            return factoryBean.getConstraintValidatorFactory().getInstance(key);
        }

        @Override
        public void releaseInstance(ConstraintValidator<?, ?> instance) {
            factoryBean.getConstraintValidatorFactory().releaseInstance(instance);
        }
    }
}