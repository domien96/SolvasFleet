package solvas.rest.api.mappers.exceptions;

/**
 * Created by David Vandorpe.
 */
public class FieldNotFoundException extends Exception {
    public FieldNotFoundException(Exception e) {
        super(e);
    }
}
