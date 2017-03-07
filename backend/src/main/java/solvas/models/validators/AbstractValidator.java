package solvas.models.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import solvas.models.Company;
import solvas.models.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by David Vandorpe.
 */
public abstract class AbstractValidator<T extends Model> implements Validator {
    private final List<String> requiredAttributes = new ArrayList<>();
    private final Class clazz;

    /**
     * @param clazz The class this validator supports
     */
    protected AbstractValidator(Class clazz) {
        this.clazz = clazz;
    }


    @Override
    public final void validate(Object target, Errors errors) {
        // Validate required attributes
        requiredAttributes.forEach( field ->
                ValidationUtils.rejectIfEmpty(errors, field, String.format("%1s.empty", field))
        );
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return this.clazz.equals(clazz);
    }

    protected void require(String ...attributes) {
        requiredAttributes.addAll(Arrays.asList(attributes));
    }
}
