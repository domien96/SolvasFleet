package solvas.authentication.jwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * Json Web Token
 */
public interface JwtToken {
    /**
     * @return The string representation of the token
     */
    String getToken();

    /**
     * @return The claims of this token
     */
    Claims getClaims();
}