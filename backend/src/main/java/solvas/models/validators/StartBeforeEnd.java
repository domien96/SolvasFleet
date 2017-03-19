package solvas.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates that the end date is equal to or after the start date.
 *
 * @author Niko Strijbol
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = StartBeforeEndValidator.class)
@Documented
@SuppressWarnings({"unused", "squid:UndocumentedApi"})
public @interface StartBeforeEnd {

    String message() default "End date must be after or equal to start date (or null).";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

    /**
     * The name of the bean containing the start date.
     *
     * @return The name of the bean.
     */
    String startDate() default "startDate";

    /**
     * The name of the bean containing the end date.
     *
     * @return The name of the bean.
     */
    String endDate() default "endDate";
}