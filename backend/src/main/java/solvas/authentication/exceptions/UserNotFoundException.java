package solvas.authentication.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * Created by david on 29/03/17.
 */
public class UserNotFoundException extends AuthenticationServiceException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}