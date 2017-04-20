package solvas.rest.utils.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Ensure vat number is unique for each company
 */
@Documented  
@Constraint(validatedBy = { UniqueVatNumberForCompanyValidator.class })
@Target({ FIELD })
@Retention(RUNTIME)  
public @interface UniqueVatNumber {
    String message() default "{org.hibernate.validator.constraints.UniqueVatNumber.message}";

    Class<?>[] groups() default { };  
  
    Class<? extends Payload>[] payload() default { };  
} 