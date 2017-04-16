package solvas.persistence.api;

/**
 * Exception is thrown when user searches for an entity which does not exist (example: non-existent id)
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException() { super();}

    public EntityNotFoundException(String s) {
        super(s);
    }
}
