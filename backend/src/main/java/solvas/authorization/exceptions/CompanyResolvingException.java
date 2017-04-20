package solvas.authorization.exceptions;

/**
 * Exception thrown when something went wrong during the resolving of the company id
 */
public class CompanyResolvingException extends RuntimeException {
    /**
     * Create instance
     * @param msg Error message with additional information
     */
    public CompanyResolvingException(String msg) {
        super(msg);
    }
}
