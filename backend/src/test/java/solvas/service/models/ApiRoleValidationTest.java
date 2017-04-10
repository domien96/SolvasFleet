package solvas.service.models;

import org.junit.Test;
import solvas.rest.api.models.ApiRole;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test that {@link ApiRole}'s validations work.
 *
 * @author Niko Strijbol
 */
public class ApiRoleValidationTest extends ValidationTest {

    private static final String START_DATE_FIELD = "startDate";
    private static final String END_DATE_FIELD = "endDate";

    /**
     * Test a valid instance.
     */
    @Test
    public void testValid() {
        ApiRole role = new ApiRole();
        role.setFunction("TEST");
        assertEquals(0, validator.validate(role).size());
    }

    /**
     * Test a role that is empty and has no function (= empty).
     */
    @Test
    public void testEmptyAndNoFunction() {
        final String function = "function";
        ApiRole role = new ApiRole();
        role.setFunction("");
        Set<ConstraintViolation<ApiRole>> v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals(function, v.iterator().next().getPropertyPath().iterator().next().getName());
        role.setFunction(null);
        v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals(function, v.iterator().next().getPropertyPath().iterator().next().getName());
    }
}