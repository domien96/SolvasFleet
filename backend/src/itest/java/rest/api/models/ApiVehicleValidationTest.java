package rest.api.models;

import org.junit.Test;
import solvas.rest.api.models.ApiVehicle;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

/**
 * Test that {@link ApiVehicle}'s validation works.
 *
 * @author Niko Strijbol
 */
@SuppressWarnings({"squid:UndocumentedApi", "squid:S109"})
public class ApiVehicleValidationTest extends ValidationTest {

    private static final String VALID_VIN = "JM3KE4CY5F0442856";

    @Test
    public void testValid() {
        ApiVehicle vehicle = random(ApiVehicle.class);
        vehicle.setVin(VALID_VIN);
        vehicle.setYear(2014);
        vehicle.setMileage(2000);
        vehicle.setValue(10);

        assertEquals(0, validator.validate(vehicle).size());
    }

    @Test
    public void testVin() {
        String vinField = "vin";
        ApiVehicle vehicle = random(ApiVehicle.class, vinField);
        vehicle.setYear(2014);
        vehicle.setMileage(2000);
        vehicle.setValue(10);
        vehicle.setVin("JM3KE4CY65655F0442856");

        Set<ConstraintViolation<ApiVehicle>> v = validator.validate(vehicle);
        assertEquals(1, v.size());
        assertEquals(vinField, v.iterator().next().getPropertyPath().iterator().next().getName());

        vehicle.setVin("");
        validator.validate(vehicle);
        assertEquals(1, v.size());
        assertEquals(vinField, v.iterator().next().getPropertyPath().iterator().next().getName());

        vehicle.setVin(null);
        validator.validate(vehicle);
        assertEquals(1, v.size());
        assertEquals(vinField, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    @Test
    public void testNumbers() {
        ApiVehicle vehicle = random(ApiVehicle.class);
        vehicle.setYear(1500);
        vehicle.setMileage(-50);
        vehicle.setValue(-99693);
        vehicle.setVin(VALID_VIN);

        assertEquals(3, validator.validate(vehicle).size());
    }

    @Test
    public void testEmptyAndNull() {
        ApiVehicle vehicle = new ApiVehicle();
        vehicle.setYear(2000);
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