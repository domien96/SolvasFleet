package solvas.models;

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
        assertEquals(0, validator.validate(fleet).size());
    }

    /**
     * First test an instance without any fields set, then test the instance if the fields are empty.
     */
    @Test
    public void testNoneAndEmpty() {
        ApiFleet fleet = new ApiFleet();
        assertEquals(1, validator.validate(fleet).size());
        fleet.setName("");
        assertEquals(1, validator.validate(fleet).size());
    }
}