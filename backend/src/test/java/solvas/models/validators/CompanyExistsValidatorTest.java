package solvas.models.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import solvas.models.Company;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * @author Niko Strijbol
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyExistsValidatorTest {

    @Mock
    private CompanyDao dao;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private CompanyExists annotation;

    private static int validCompanyId = 500;

    @Before
    public void setUp() throws Exception {
        Company company = new Company();
        company.setId(validCompanyId);
        when(dao.find(anyInt())).then(invocation -> {
            if ((int) invocation.getArguments()[0] == validCompanyId) {
                return company;
            } else {
                throw new EntityNotFoundException();
            }
        });
    }

    /**
     * Test the isValid method.
     */
    @Test
    public void testIsValid() {
        CompanyExistsValidator validator = new CompanyExistsValidator(dao);
        validator.initialize(annotation);
        assertTrue(validator.isValid(validCompanyId, context));
        assertFalse(validator.isValid(0, context));
        assertFalse(validator.isValid(86565, context));
        assertFalse(validator.isValid(-1, context));
    }
}