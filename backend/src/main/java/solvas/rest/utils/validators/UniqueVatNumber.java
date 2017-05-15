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
 * Ensure vat number is unique for a company.
 */
@Documented  
@Constraint(validatedBy = { UniqueVatNumberForCompanyValidator.class })
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)  
public @interface UniqueVatNumber {
    /**
     * @return  Validation message
     */
    String message() default "{solvas.rest.utils.validators.UniqueVatNumber.message}";

    /**
     * @return Optional validation group.
     */
    Class<?>[] groups() default {};

    /**
     * @return Optional payload.
     */
    Class<? extends Payload>[] payload() default { };
} 