package solvas.service.models.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * Checks if a LocalDatetime is after a given moment.
 * Only usable for fields with type LocalDateTime.
 *
 * @author domien on 3/05/2017.
 */
public class AfterLocalDatetimeValidator implements ConstraintValidator<AfterLocalDateTime, LocalDateTime> {
    /**
     * The earliest localDatetime allowed.
     */
    private LocalDateTime localDateTime;

    @Override
    public void initialize(AfterLocalDateTime ca) {
        this.localDateTime = LocalDateTime.of(ca.year(),ca.month(),ca.dayOfMonth(),ca.hour(),ca.minute());
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        if (value == null || (value.isEqual(localDateTime) || value.isAfter(localDateTime))) {
            return true;
        } else {
            return false;
        }
    }
}
