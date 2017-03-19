package solvas.models.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import solvas.TestUtils;
import solvas.persistence.api.dao.CompanyDao;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Niko Strijbol
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyExistsValidatorTest {

    private CompanyDao dao;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private CompanyExists annotation;

    @Before
    public void setUp() throws Exception {
        dao = TestUtils.mockedCompanyDao();
    }

    /**
     * Test the isValid method.
     */
    @Test
    public void testIsValid() {
        CompanyExistsValidator validator = new CompanyExistsValidator(dao);
        validator.initialize(annotation);
        assertTrue(validator.isValid(TestUtils.VALID_COMPANY, context));
        assertFalse(validator.isValid(0, context));
        assertFalse(validator.isValid(86565, context));
        assertFalse(validator.isValid(-1, context));
    }
}