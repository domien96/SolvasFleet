package solvas.service.models;

import org.junit.Test;
import solvas.rest.api.models.ApiRole;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Test that {@link ApiRole}'s validations work.
 *
 * @author Niko Strijbol
 */
public class ApiRoleValidationTest extends ValidationTest {

    /**
     * Test a valid instance.
     */
    @Test
    public void testValid() {
        ApiRole role = new ApiRole();
        role.setName("TEST");
        assertEquals(0, validator.validate(role).size());
    }

    /**
     * Test a role that is empty and has no function (= empty).
     */
    @Test
    public void testEmptyAndNoFunction() {
        final String function = "name";
        ApiRole role = new ApiRole();
        role.setName("");
        Set<ConstraintViolation<ApiRole>> v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals(function, v.iterator().next().getPropertyPath().iterator().next().getName());
        role.setName(null);
        v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals(function, v.iterator().next().getPropertyPath().iterator().next().getName());
    }
}