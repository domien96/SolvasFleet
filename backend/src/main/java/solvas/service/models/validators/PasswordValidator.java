package solvas.service.models.validators;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Checks that the password is not null when id is 0
 *
 * @author Steven Bastiaens
 */
public class PasswordValidator implements ConstraintValidator<Password, Object> {

    private String id;
    private String password;

    @Override
    public void initialize(Password constraintAnnotation) {
        id = constraintAnnotation.id();
        password = constraintAnnotation.password();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BeanWrapper wrapper = new BeanWrapperImpl(value);

        int idV = (int) wrapper.getPropertyValue(id);
        String passwordV = (String) wrapper.getPropertyValue(password);

        if (idV !=0 ||passwordV!=null){
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(Password.MESSAGE)
                    .addPropertyNode(password)
                    .addConstraintViolation();
            return false;
        }
    }
}