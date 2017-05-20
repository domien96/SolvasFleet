package solvas.rest.utils.validators;

import solvas.service.models.Commission;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Ensure the fields of the {@link Commission} class are filled in from top.
 * This means that if for example the vehicle type is filled in, then the fleet and company must also be filled in
 * since these fields are on top (a more general concept) than vehicle type.
 * Created by domien on 20/05/2017.
 */
@Documented
@Constraint(validatedBy = {CommissionFieldsFilledInOrderValidator.class})
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
public @interface CommissionFieldsFilledInOrder {
    /**
     * @return  Validation message
     */
    String message() default "Fields of commission are not filled in order";

    /**
     * @return Optional validation group.
     */
    Class<?>[] groups() default {};

    /**
     * @return Optional payload.
     */
    Class<? extends Payload>[] payload() default { };
}
