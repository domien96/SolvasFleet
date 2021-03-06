package solvas.service.mappers.exceptions;

/**
 * Thrown when a dependency could not be found by the code.
 * @author Niko Strijbol
 */
public class DependantEntityNotFound extends Exception {

    private final String field;

    /**
     * Constructor.
     *
     * @param field The field that cause the error.
     * @param cause The underlying cause.
     */
    public DependantEntityNotFound(String field, Throwable cause) {
        super(cause);
        this.field = field;
    }

    /**
     * @return The field.
     */
    public String getField() {
        return field;
    }

    public String getEntityMessage() {
        return "Entity does not exist.";
    }
}
