package solvas.service.models;

import org.junit.Test;
import solvas.rest.api.models.ApiFleet;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

/**
 * @author Niko Strijbol
 */
public class ApiFleetValidationTest extends ValidationTest {

    /**
     * Test a valid instance.
     */
    @Test
    public void testValid() {
        ApiFleet fleet = random(ApiFleet.class);
        fleet.setPaymentPeriod(10);
        fleet.setFacturationPeriod(10);
        assertEquals(0, validator.validate(fleet).size());
    }

    /**
     * First test an instance without any fields set, then test the instance if the fields are empty.
     */
    @Test
    public void testNoneAndEmpty() {
        ApiFleet fleet = new ApiFleet();
        assertEquals(3, validator.validate(fleet).size());
        fleet.setName("");
        fleet.setFacturationPeriod(-1);
        fleet.setPaymentPeriod(-1);
        assertEquals(3, validator.validate(fleet).size());
    }
}