package solvas.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validator annotation for a phone number.
 *
 * @author Niko Strijbol
 */
@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface PhoneNumber {

    /**
     * @return The message for when the validation fails.
     */
    String message() default "Invalid phone number.";

    /**
     * @return Optional validation group.
     */
    Class<?>[] groups() default {};

    /**
     * @return Optional payload.
     */
    Class<? extends Payload>[] payload() default { };

    /**
     * The two letter ISO 3166 country code. Must be in capitals.
     *
     * @see <a href="https://www.iso.org/iso-3166-country-codes.html">List of country codes.</a>
     *
     * @return The country code.
     */
    String countryCode() default "BE";
}