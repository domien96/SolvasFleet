package solvas.authorization.exceptions;

/**
 * Trying to get permissions for an unsupported resource type
 */
public class UnsupportedResourceType extends RuntimeException {
    /**
     * Create instance
     * @param msg Error message with additional information
     */
    public UnsupportedResourceType(String msg) {
        super(msg);
    }
}
