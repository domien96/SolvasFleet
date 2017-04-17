package solvas.persistence.api;

/**
 * Exception is thrown when user searches for an entity which does not exist (example: non-existent id)
 */
public class EntityNotFoundException extends Exception {

    /**
     * No-arg constructor
     */
    public EntityNotFoundException() {
        super();
    }

    /**
     * @param msg Exception message
     */
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
