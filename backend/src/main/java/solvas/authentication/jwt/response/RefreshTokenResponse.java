package solvas.authentication.jwt.response;

import io.jsonwebtoken.Claims;
import solvas.authentication.jwt.token.JwtToken;

/**
 * Represents a response containing a refresh token
 */
public class RefreshTokenResponse implements TokenResponse {
    private final String token;
    private final Claims claims;

    /**
     * @param refreshToken The refresh token this response will represent
     */
    RefreshTokenResponse(JwtToken refreshToken) {
        token = refreshToken.getToken();
        claims = refreshToken.getClaims();
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
