package solvas.rest.api.models;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

/**
 * @author Niko Strijbol
 */
public class ApiUserValidationTest extends ValidationTest {

    /**
     * Test valid instance.
     */
    @Test
    public void testValid() {
        ApiUser user = random(ApiUser.class);
        user.setEmail("test@example.com");
        assertEquals(0, validator.validate(user).size());
    }

    /**
     * Test values for the email.
     */
    @Test
    public void testEmail() {
        String emailField = "email";
        ApiUser user = random(ApiUser.class, emailField);

        Set<ConstraintViolation<ApiUser>> v = validator.validate(user);
        assertEquals(1, v.size());
        assertEquals(emailField, v.iterator().next().getPropertyPath().iterator().next().getName());

        user.setEmail("VERY BAD EMAIL");
        v = validator.validate(user);
        assertEquals(1, v.size());
        assertEquals(emailField, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    /**
     * Test nulls and empty values.
     */
    @Test
    public void testNullsAndEmpty() {
        ApiUser user = new ApiUser();
        assertEquals(4, validator.validate(user).size());
        user.setFirstName("");
        user.setLastName("");
        user.setPassword("");
        assertEquals(4, validator.validate(user).size());
    }
}