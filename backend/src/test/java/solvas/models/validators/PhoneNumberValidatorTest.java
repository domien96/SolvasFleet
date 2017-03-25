package solvas.models.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Niko Strijbol
 */
@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private PhoneNumber number;

    @Before
    public void setUp() throws IllegalAccessException, InstantiationException {
        when(number.countryCode()).thenReturn("BE");
    }

    /**
     * Test a few obvious phone numbers to test if the validator is correct. The aim is NOT to test
     * the actual validation.
     */
    @Test
    public void testIsValid() {
        PhoneNumberValidator validator = new PhoneNumberValidator();
        validator.initialize(number);
        assertTrue(validator.isValid("+32 56 22 56 00", context));
        assertTrue(validator.isValid("00 32 56 22 56 00", context));
        assertTrue(validator.isValid("056225600", context));
        assertTrue(validator.isValid("+1 1-877-355-5787", context));
        assertFalse(validator.isValid("", context));
        assertFalse(validator.isValid("YO WAT EEN NUMMER is DAT HIER!", context));
        assertTrue(validator.isValid(null, context));
    }
}