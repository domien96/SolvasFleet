package solvas.models.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import solvas.models.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Contains boilerplate code for validators
 * Added validations are ran in order of addition
 * @author David Vandorpe
 * @param <T> Type of model that should be validated
 */
public abstract class AbstractValidator<T extends Model> implements Validator {
    private final List<Validation<T>> validations = new ArrayList<>();
    private final Class<? extends Model> clazz;

    /**
     * @param clazz The class this validator supports
     */
    protected AbstractValidator(Class<? extends Model> clazz) {
        this.clazz = clazz;
    }


    @Override
    public final void validate(Object target, Errors errors) {
        T model = (T) target;
        validations.forEach(validation -> validation.run(model, errors));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return this.clazz.equals(clazz);
    }

    /**
     * Add validations that check if an attribute is not null and not the empty string.
     * @param attributes The attributes that are required (varargs)
     */
    protected void require(String ...attributes) {
         Arrays.stream(attributes).map(attribute ->
                 (Validation<T>) (target, errors) ->
                         ValidationUtils.rejectIfEmpty(errors, attribute, String.format("%1s.empty", attribute)))
            .forEach(validations::add);
    }

    /**
     * Register new validations
     * @param validations The validations to be added (varargs)
     */
    protected void addValidations(Validation<T> ...validations) {
        this.validations.addAll(Arrays.asList(validations));
    }

    /**
     * Register a new validation
     * @param validation The validation to be added
     */
    protected void addValidation(Validation<T> validation) {
        this.validations.add(validation);
    }

    /**
     * A custom validation
     * @param <T> Type of model that should be validated
     */
    public interface Validation<T extends Model> {
        /**
         * @param target The entity to be validated
         * @param errors The errors for this entity
         */
        void run(T target, Errors errors);
    }
}
