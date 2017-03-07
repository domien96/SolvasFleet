package solvas.models.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import solvas.models.Company;

import java.util.Arrays;

/**
 * Validate Companies
 *
 * @author David Vandorpe.
 */
@Component
public class CompanyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Company.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Arrays.asList("name", "vatNumber", "phoneNumber", "address", "url").forEach(field -> {
                    ValidationUtils.rejectIfEmpty(errors, field, String.format("%1s.empty", field));
                }
        );
    }
}
