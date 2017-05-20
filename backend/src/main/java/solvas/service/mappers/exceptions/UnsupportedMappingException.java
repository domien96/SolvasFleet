package solvas.service.mappers.exceptions;

/**
 * Exception thrown when a mapping isn't supported
 */
public class UnsupportedMappingException extends RuntimeException {
    /**
     * Create instance
     * @param msg Exception message
     */
    public UnsupportedMappingException(String msg) {
        super(msg);
    }
}
