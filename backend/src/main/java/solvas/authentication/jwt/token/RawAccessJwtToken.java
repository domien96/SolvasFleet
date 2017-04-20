package solvas.authentication.jwt.token;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.BadCredentialsException;
import solvas.authentication.exceptions.JwtExpiredTokenException;

/**
 * Raw unverified JWT
 */
public class RawAccessJwtToken implements JwtToken {

    private String token;

    /**
     * Create an unverified JWT
     * @param token String representation of the token
     */
    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    /**
     * Parses and validates JWT Token signature.
     *
     * @param signingKey Secret key to sign tokens
     * @return The Claims of this token
     *
     * @throws BadCredentialsException Invalid JWT
     * @throws JwtExpiredTokenException JWT has reached it's expiry date
     * 
     */
    public Jws<Claims> parseClaims(String signingKey) throws BadCredentialsException, ExpiredJwtException {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(this.token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new BadCredentialsException("Invalid JWT token: ", ex);
        } catch (ExpiredJwtException expiredEx) {
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        }
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public Claims getClaims() {
        return null;
    }
}