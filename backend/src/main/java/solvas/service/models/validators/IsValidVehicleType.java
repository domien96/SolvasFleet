package solvas.service.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to verify if a vehicle type (as string) is a valid, existent vehicle type.
 *
 * @author  domien on 14/05/2017.
 */
@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = IsValidVehicleTypeValidator.class)
@Documented
public @interface IsValidVehicleType {
    String MESSAGE = "Vehicletype is unknown. (it has no matching object in service & persistence layer.)";

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
}
