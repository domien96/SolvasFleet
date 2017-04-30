package solvas.service.models;

import org.joda.time.DateTime;
import org.junit.Test;
import solvas.rest.api.models.ApiVehicle;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

/**
 * Test that {@link ApiVehicle}'s validation works.
 *
 * @author Niko Strijbol
 */
public class ApiVehicleValidationTest extends ValidationTest {

    private static final String VALID_VIN = "JM3KE4CY5F0442856";
    private static final String INVALID_VIN = "JM3KE4CY65655F0442856";

    /**
     * Test a valid instance.
     */
    @Test
    public void testValid() {
        ApiVehicle vehicle = random(ApiVehicle.class);
        vehicle.setVin(VALID_VIN);
        vehicle.setYear(LocalDateTime.of(2014,1,1,0,0));
        vehicle.setMileage(2000);
        vehicle.setValue(10);

        assertEquals(0, validator.validate(vehicle).size());
    }

    /**
     * Test that the vin number is being validated.
     */
    @Test
    public void testVin() {
        String vinField = "vin";
        ApiVehicle vehicle = random(ApiVehicle.class, vinField);
        vehicle.setYear(LocalDateTime.of(2014,1,1,0,0));
        vehicle.setMileage(2000);
        vehicle.setValue(10);
        vehicle.setVin(INVALID_VIN);

        Set<ConstraintViolation<ApiVehicle>> v = validator.validate(vehicle);
        assertEquals(1, v.size());
        assertEquals(vinField, v.iterator().next().getPropertyPath().iterator().next().getName());

        vehicle.setVin("");
        v = validator.validate(vehicle);
        assertEquals(1, v.size());
        assertEquals(vinField, v.iterator().next().getPropertyPath().iterator().next().getName());

        vehicle.setVin(null);
        v = validator.validate(vehicle);
        assertEquals(1, v.size());
        assertEquals(vinField, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    /**
     * Test the numerical fields.
     */
    @Test
    public void testNumbers() {
        ApiVehicle vehicle = random(ApiVehicle.class);
        vehicle.setYear(LocalDateTime.of(1500,1,1,0,0));
        vehicle.setMileage(-50);
        vehicle.setValue(-99693);
        vehicle.setVin(VALID_VIN);

        assertEquals(3, validator.validate(vehicle).size());
    }

    /**
     * Test empty and null fields.
     */
    @Test
    public void testEmptyAndNull() {
        ApiVehicle vehicle = new ApiVehicle();
        vehicle.setYear(LocalDateTime.of(2000,1,1,0,0));
        vehicle.setMileage(6565);
        vehicle.setValue(63);

        assertEquals(4, validator.validate(vehicle).size());

        vehicle.setVin(VALID_VIN);
        vehicle.setBrand("");
        vehicle.setModel("");
        vehicle.setType("");
        assertEquals(3, validator.validate(vehicle).size());
    }
}