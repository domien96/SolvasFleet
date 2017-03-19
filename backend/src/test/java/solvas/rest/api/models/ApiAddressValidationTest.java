package solvas.rest.api.models;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

/**
 * @author Niko Strijbol
 */
public class ApiAddressValidationTest extends ValidationTest {

    private static final String CITY_FIELD = "city";

    /**
     * Test valid instance.
     */
    @Test
    public void testValid() {
        ApiAddress apiAddress = random(ApiAddress.class);
        assertEquals(0, validator.validate(apiAddress).size());
    }

    /**
     * Test instance with missing field.
     */
    @Test
    public void testMissing() {
        ApiAddress apiAddress = random(ApiAddress.class, CITY_FIELD);
        Set<ConstraintViolation<ApiAddress>> v = validator.validate(apiAddress);
        assertEquals(1, v.size());
        assertEquals(CITY_FIELD, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    /**
     * Test instance with multiple missing fields.
     */
    @Test
    public void testMultiple() {
        ApiAddress apiAddress = random(ApiAddress.class, CITY_FIELD, "street", "postalCode");
        assertEquals(3, validator.validate(apiAddress).size());
    }

    /**
     * Test for empty fields.
     */
    @Test
    public void testEmpty() {
        ApiAddress apiAddress = random(ApiAddress.class, CITY_FIELD);
        apiAddress.setCity("");
        Set<ConstraintViolation<ApiAddress>> v = validator.validate(apiAddress);
        assertEquals(1, v.size());
        assertEquals(CITY_FIELD, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    /**
     * Test instance with everything null.
     */
    @Test
    public void testNone() {
        ApiAddress apiAddress = new ApiAddress();
        assertEquals(5, validator.validate(apiAddress).size());
    }
}