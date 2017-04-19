package solvas.persistence.api;

/**
 * Exception is thrown when user searches for an entity which does not exist (example: non-existent id)
 */
public class EntityNotFoundException extends Exception {

    /**
     * Constructs exception without any message
     */
    public EntityNotFoundException() { super();}

    /**
     * Construct with a message
     * @param s Message
     */
    public EntityNotFoundException(String s) {
        super(s);
    }
}
