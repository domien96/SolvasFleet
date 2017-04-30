package solvas.authentication.exceptions;

import org.springframework.security.core.AuthenticationException;
import solvas.authentication.jwt.token.JwtToken;

/**
 * This exception is raised when an expired JWT is passed by a user.
 */
public class JwtExpiredTokenException extends AuthenticationException {
    private static final long serialVersionUID = -5959543783324224864L;

    private JwtToken token;

    /**
     * @param token The expired JWT
     * @param msg Custom error message
     * @param t Nested throwable
     */
    public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
        super(msg, t);
        this.token = token;
    }

    /**
     *
     * @return The invalid token
     */
    public String token() {
        return this.token.getToken();
    }
}
