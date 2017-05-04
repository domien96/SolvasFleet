package solvas.rest.api.models.errors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import solvas.rest.utils.JsonListWrapper;

import javax.validation.metadata.ConstraintDescriptor;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Encapsulates error responses for the API.
 *
 * @author Niko Strijbol
 */
public class ApiError {

    private final ErrorType type;
    private final String field;
    private final String message;

    public ApiError(ErrorType type, String field, String message) {
        this.type = type;
        this.field = field;
        this.message = message;
    }

    public ApiError(FieldError error) {
        this.message = error.getDefaultMessage();
        this.field = error.getField();
        if (error.isBindingFailure()) {
            this.type = ErrorType.WRONG_TYPE;
        } else {
            this.type = ErrorType.fromAnnotationName(error.getCode());
        }
    }

    public ErrorType getType() {
        return type;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Construct an API error response from a binding result.
     *
     * Implementation note: to translate the cause to the API key, this method depends on the default behaviour of
     * {@link org.springframework.validation.beanvalidation.SpringValidatorAdapter#determineErrorCode(ConstraintDescriptor)}.
     * If this implementation changes or is overridden, this method will no longer work.
     *
     * @param result The binding result.
     *
     * @return The object.
     */
    public static JsonListWrapper<ApiError> from(BindingResult result) {
        List<ApiError> errors = result.getFieldErrors().stream()
                .map(ApiError::new)
                .collect(Collectors.toList());

        return new JsonListWrapper<>(errors, JsonListWrapper.ERROR_KEY);
    }
}