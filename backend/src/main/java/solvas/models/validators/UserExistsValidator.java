package solvas.models.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.UserDao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Checks that an user exists.
 *
 * @author Niko Strijbol
 */
@Transactional(readOnly = true)
public class UserExistsValidator implements ConstraintValidator<UserExists, Integer> {

    private final UserDao userDao;

    /**
     * Constructor.
     *
     * @param userDao The user dao to use.
     */
    @Autowired
    public UserExistsValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void initialize(UserExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        try {
            userDao.find(value);
            return true;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }
}