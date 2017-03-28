package solvas.models.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * @author Niko Strijbol
 */
@RunWith(MockitoJUnitRunner.class)
public class VinValidatorTest {

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private Vin annotation;

    /**
     * Test a few obvious phone numbers to test if the validator is correct. The aim is NOT to test
     * the actual validation.
     */
    @Test
    public void testIsValid() {
        VinValidator validator = new VinValidator();
        validator.initialize(annotation);
        assertTrue(validator.isValid("2CNDL63F556045005", context));
        assertTrue(validator.isValid("1C6RR7GT1ES161496", context));
        assertTrue(validator.isValid("JTHBF30G120069075", context));
        assertTrue(validator.isValid("JTHB-F30G 1200-69075", context));
        assertFalse(validator.isValid("+1 1-877-355-5787", context));
        assertFalse(validator.isValid("", context));
        assertTrue(validator.isValid(null, context));
    }
}