package solvas.rest.utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Ensures that a VIN is unique in the database.
 *
 * @author Niko Strijbol
 */
@Documented
@Constraint(validatedBy = {UniqueVinForVehicleValidator.class})
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface UniqueVin {

    /**
     * @return Validation message
     */
    String message() default "{solvas.rest.utils.validators.UniqueVin.message}";

    /**
     * @return Optional validation group.
     */
    Class<?>[] groups() default {};

    /**
     * @return Optional payload.
     */
    Class<? extends Payload>[] payload() default {};
}