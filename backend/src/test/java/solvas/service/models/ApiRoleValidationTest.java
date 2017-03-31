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
        role.setStartDate(LocalDateTime.now());
        assertEquals(0, validator.validate(role).size());
    }

    /**
     * Test a role that is empty and has no function (= empty).
     */
    @Test
    public void testEmptyAndNoFunction() {
        final String function = "function";
        ApiRole role = new ApiRole();
        role.setStartDate(LocalDateTime.now());
        role.setFunction("");
        Set<ConstraintViolation<ApiRole>> v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals(function, v.iterator().next().getPropertyPath().iterator().next().getName());
        role.setFunction(null);
        v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals(function, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    /**
     * Test an instance without a start date.
     */
    @Test
    public void testNullStartDate() {
        ApiRole role = new ApiRole();
        role.setFunction("random");
        Set<ConstraintViolation<ApiRole>> v = validator.validate(role);
        assertEquals(1, v.size());
        assertEquals(START_DATE_FIELD, v.iterator().next().getPropertyPath().iterator().next().getName());
    }

    /**
     * Test date logic, like that the end date must be equal to or after the start date.
     */
    @Test
    public void testDates() {
        ApiRole role = new ApiRole();
        role.setFunction("user");
        LocalDateTime now = LocalDateTime.now();
        role.setStartDate(now);

        role.setEndDate(now.plusYears(5));
        assertEquals(0, validator.validate(role).size());

        role.setEndDate(now);
        assertEquals(0, validator.validate(role).size());

        role.setEndDate(now.minusYears(5));
        Collection<ConstraintViolation<ApiRole>> v = validator.validate(role);
        assertEquals(2, validator.validate(role).size());

        for (ConstraintViolation<ApiRole> next: v){
            assertTrue(next.getPropertyPath().iterator().next().getName().equals(START_DATE_FIELD) ||
                    next.getPropertyPath().iterator().next().getName().equals(END_DATE_FIELD)
            );
        }
    }
}