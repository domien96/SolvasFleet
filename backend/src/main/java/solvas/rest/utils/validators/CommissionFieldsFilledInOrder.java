package solvas.rest.utils.validators;

import solvas.service.models.Commission;

import javax.validation.Payload;

/**
 * Ensure the fields of the {@link Commission} class are filled in from top.
 * This means that if for example the vehicle type is filled in, then the fleet and company must also be filled in
 * since these fields are on top (a more general concept) than vehicle type.
 * Created by domien on 20/05/2017.
 */
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
