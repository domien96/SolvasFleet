package solvas.authentication.jwt.token;

import io.jsonwebtoken.Claims;

/**
 * Raw representation of JWT Token.
 */
public final class AccessToken implements JwtToken {
    private final String rawToken;
    private Claims claims;

    /**
     * @param token The token in String format
     * @param claims Set of claims (see JWT specs)
     */
    public AccessToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    @Override
    public String getToken() {
        return this.rawToken;
    }

    /**
     * @return The claims this JWT makes
     */
    public Claims getClaims() {
        return claims;
    }
}