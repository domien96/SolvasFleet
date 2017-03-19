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

    @Test
    public void testValid() {
        ApiAddress apiAddress = random(ApiAddress.class);
        assertEquals(0, validator.validate(apiAddress).size());
    }

    @Test
    public void testMissing() {
        final String missingField = "city";
        ApiAddress apiAddress = random(ApiAddress.class, missingField);
        Set<ConstraintViolation<ApiAddress>> v = validator.validate(apiAddress);
        assertEquals(1, v.size());
        assertEquals(missingField, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    @Test
    public void testMultiple() {
        ApiAddress apiAddress = random(ApiAddress.class, "city", "street", "postalCode");
        assertEquals(3, validator.validate(apiAddress).size());
    }

    @Test
    public void testEmpty() {
        final String missingField = "city";
        ApiAddress apiAddress = random(ApiAddress.class, missingField);
        apiAddress.setCity("");
        Set<ConstraintViolation<ApiAddress>> v = validator.validate(apiAddress);
        assertEquals(1, v.size());
        assertEquals(missingField, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    @Test
    public void testNone() {
        ApiAddress apiAddress = new ApiAddress();
        assertEquals(5, validator.validate(apiAddress).size());
    }
}