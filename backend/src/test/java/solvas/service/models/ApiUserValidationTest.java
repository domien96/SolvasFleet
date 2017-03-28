package solvas.service.models;

import org.junit.Test;
import solvas.rest.api.models.ApiUser;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.junit.Assert.assertEquals;

/**
 * Test that {@link ApiUser}'s validations work.
 *
 * @author Niko Strijbol
 */
public class ApiUserValidationTest extends ValidationTest {

    /**
     * Test a valid instance.
     */
    @Test
    public void testValid() {
        ApiUser user = random(ApiUser.class);
        user.setEmail("test@example.com");
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
        assertEquals(4, validator.validate(user).size()); //4 erros Password not null, lastname,email,firstname
        user.setFirstName("");
        user.setLastName("");
        user.setPassword("");
        assertEquals(3, validator.validate(user).size());
    }
}