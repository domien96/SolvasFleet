package solvas.models.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import solvas.TestUtils;
import solvas.persistence.api.dao.UserDao;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Niko Strijbol
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"squid:UndocumentedApi", "squid:S109"})
public class UserExistsValidatorTest {

    private UserDao dao;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private UserExists annotation;

    @Before
    public void setUp() throws Exception {
        dao = TestUtils.mockedUserDao();
    }

    @Test
    public void testIsValid() {
        UserExistsValidator validator = new UserExistsValidator(dao);
        validator.initialize(annotation);
        assertTrue(validator.isValid(TestUtils.VALID_USER, context));
        assertFalse(validator.isValid(0, context));
        assertFalse(validator.isValid(86565, context));
        assertFalse(validator.isValid(-1, context));
    }
}