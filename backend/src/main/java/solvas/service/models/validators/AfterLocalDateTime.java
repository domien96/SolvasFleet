package solvas.service.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validation annotation to check if a LocalDatetime is after a given moment.
 * @author domien on 3/05/2017.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = AfterLocalDatetimeValidator.class)
@Documented
public @interface AfterLocalDateTime {
    String MESSAGE = "Datetime cannot be before the given date and time";

    /**
     * @return The message for when the validation fails.
     */
    String message() default MESSAGE;

    /**
     * @return Optional validation group.
     */
    Class<?>[] groups() default {};

    /**
     * @return Optional payload.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * @return year
     */
    int year();

    /**
     * @return month
     */
    int month();

    /**
     * @return day of the month
     */
    int dayOfMonth();

    /**
     * @return hour
     */
    int hour();

    /**
     * @return minute
     */
    int minute();
}
