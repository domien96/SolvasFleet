package solvas.rest.api.mappers.exceptions;

/**
 * @author David Vandorpe
 *
 * Exception thrown when trying to copy a nonexistent field between the ApiModel and Persistence Model
 */
public class FieldNotFoundException extends Exception {

    /**
     * Create exception from nested exception
     * @param e Nested exception
     */
    public FieldNotFoundException(Exception e) {
        super(e);
    }
}
