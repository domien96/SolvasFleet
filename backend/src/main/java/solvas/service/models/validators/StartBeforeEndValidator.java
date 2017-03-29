package solvas.service.models.validators;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * Checks that the end date if equal to or after the start date. This will only work if
 * the dates are {@link LocalDateTime}s.
 *
 * @author Niko Strijbol
 */
public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, Object> {

    private String startDate;
    private String endDate;

    @Override
    public void initialize(StartBeforeEnd constraintAnnotation) {
        startDate = constraintAnnotation.startDate();
        endDate = constraintAnnotation.endDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        BeanWrapper wrapper = new BeanWrapperImpl(value);

        LocalDateTime start = (LocalDateTime) wrapper.getPropertyValue(startDate);
        LocalDateTime end = (LocalDateTime) wrapper.getPropertyValue(endDate);

        if (start == null || end == null || end.isEqual(start) || end.isAfter(start)) {
            return true;
        } else {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(StartBeforeEnd.MESSAGE)
                    .addPropertyNode(startDate)
                    .addConstraintViolation();
            context.buildConstraintViolationWithTemplate(StartBeforeEnd.MESSAGE)
                    .addPropertyNode(endDate)
                    .addConstraintViolation();
            return false;
        }
    }
}