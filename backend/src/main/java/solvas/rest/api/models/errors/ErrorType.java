package solvas.rest.api.models.errors;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import solvas.rest.utils.validators.UniqueVatNumber;
import solvas.service.models.validators.Password;
import solvas.service.models.validators.PhoneNumber;
import solvas.service.models.validators.Vin;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * The various error types defined by the API.
 *
 * @author Niko Strijbol
 */
public enum ErrorType {
    WRONG_FORMAT,
    WRONG_TYPE,
    MISSING,
    TOO_SMALL,
    TOO_BIG,
    INVALID,
    NOT_UNIQUE,
    NOT_FOUND;

    private static Map<String, ErrorType> annotationToApi;

    static {
        annotationToApi = new HashMap<>();
        annotationToApi.put(Email.class.getSimpleName(), WRONG_FORMAT);
        annotationToApi.put(NotBlank.class.getSimpleName(), MISSING);
        annotationToApi.put(NotNull.class.getSimpleName(), MISSING);
        annotationToApi.put(Password.class.getSimpleName(), MISSING);
        annotationToApi.put(PhoneNumber.class.getSimpleName(), WRONG_FORMAT);
        annotationToApi.put(Vin.class.getSimpleName(), WRONG_FORMAT);
        annotationToApi.put(Min.class.getSimpleName(), TOO_SMALL);
        annotationToApi.put(Max.class.getSimpleName(), TOO_BIG);
      //  annotationToApi.put(AfterLocalDateTime.class.getSimpleName(), TOO_SMALL);
        annotationToApi.put(UniqueVatNumber.class.getSimpleName(), NOT_UNIQUE);
    }

    /**
     * Returns the appropriate API error type based on the name of the annotation that caused the error.
     *
     * @param name The {@link Class#getSimpleName()} of the annotation.
     *
     * @return The mapped error type or {@link #INVALID} if not recognized.
     */
    public static ErrorType fromAnnotationName(String name) {
        return annotationToApi.getOrDefault(name, INVALID);
    }
}