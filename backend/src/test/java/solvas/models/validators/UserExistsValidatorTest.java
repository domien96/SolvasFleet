package solvas.models.validators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import solvas.models.User;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.UserDao;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * @author Niko Strijbol
 */
@RunWith(MockitoJUnitRunner.class)
public class UserExistsValidatorTest {

    @Mock
    private UserDao dao;

    @Mock
    private ConstraintValidatorContext context;

    @Mock
    private UserExists annotation;

    private static int validUserId = 500;

    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setId(validUserId);
        when(dao.find(anyInt())).then(invocation -> {
            if ((int) invocation.getArguments()[0] == validUserId) {
                return user;
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
        UserExistsValidator validator = new UserExistsValidator(dao);
        validator.initialize(annotation);
        assertTrue(validator.isValid(validUserId, context));
        assertFalse(validator.isValid(0, context));
        assertFalse(validator.isValid(86565, context));
        assertFalse(validator.isValid(-1, context));
    }
}