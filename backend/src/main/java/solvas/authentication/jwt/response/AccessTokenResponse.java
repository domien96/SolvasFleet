package solvas.authentication.jwt.response;

import io.jsonwebtoken.Claims;
import solvas.authentication.jwt.token.JwtToken;

/**
 * Represents a response containing an access token
 */
public class AccessTokenResponse implements TokenResponse {
    private final String token;
    private final Claims claims;

    /**
     * @param accessToken The access token the response will represent
     */
    AccessTokenResponse(JwtToken accessToken) {
        token = accessToken.getToken();
        claims = accessToken.getClaims();
    }

    /**
     * @return The claims of the access token
     */
    public Claims getClaims() {
        return claims;
    }

    /**
     * @return The String representation of the token
     */
    public String getToken() {
        return token;
    }
}
