package solvas.service.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates that the password is notnull when id is 0
 *
 * @author Steven Bastiaens
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface Password {

    String MESSAGE = "Password cannot be null with a POST request";

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
     * The name of the bean containing the id
     *
     * @return The name of the bean.
     */
    String id() default "id";

    /**
     * The name of the bean containing the password
     *
     * @return The name of the bean.
     */
    String password() default "password";
}