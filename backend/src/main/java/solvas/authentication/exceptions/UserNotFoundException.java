package solvas.authentication.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * The user that tried to authenticate could not be found
 */
public class UserNotFoundException extends AuthenticationServiceException {
    /**
     * @param msg The error message
     */
    public UserNotFoundException(String msg) {
        super(msg);
    }
}