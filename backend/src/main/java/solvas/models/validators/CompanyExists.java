package solvas.models.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validator to check that an ID exists for a company.
 *
 * @author Niko Strijbol
 */

@Target({FIELD, METHOD, PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CompanyExistsValidator.class)
@Documented
@SuppressWarnings({"unused", "squid:UndocumentedApi"})
public @interface CompanyExists {

    String message() default "Non-existing company.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };
}
