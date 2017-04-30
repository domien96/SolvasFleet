package solvas.authentication.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * Exception thrown when authentication method isn't supported
 * @author vladimir.stankovic
 *
 * Aug 4, 2016
 */
public class AuthMethodNotSupportedException extends AuthenticationServiceException {
    private static final long serialVersionUID = 3705043083010304496L;

    /**
     * @param msg The exception message
     */
    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}