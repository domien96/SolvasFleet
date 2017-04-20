package solvas.authentication.jwt.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import solvas.authentication.exceptions.InvalidJwt;

import java.util.List;
import java.util.Optional;

/**
 * Token used to refresh access tokens
 */
public class RefreshToken implements JwtToken {
    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    /**
     * Parses and validates Refresh token
     * 
     * @param token The JWT to parse
     * @param signingKey The secret key used to sign this JWT
     * 
     * @return The parsed access token
     */
    public static RefreshToken create(RawAccessJwtToken token, String signingKey) throws InvalidJwt {
        Jws<Claims> claims = token.parseClaims(signingKey);

        List<?> scopes = claims.getBody().get("scopes", List.class);
        if (scopes == null || scopes.isEmpty() 
                || scopes.stream().noneMatch(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))) {
           throw new InvalidJwt(token);
        }

        return new RefreshToken(claims);
    }

    @Override
    public String getToken() {
        return null;
    }

    /**
     * @return The claims of this token
     */
    public Claims getClaims() {
        return claims.getBody();
    }

    /**
     * @return The String representation of the subject
     */
    public String getSubject() {
        return claims.getBody().getSubject();
    }
}