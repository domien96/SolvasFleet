package solvas.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import solvas.authentication.user.UserContext;
import solvas.authentication.jwt.token.AccessToken;
import solvas.authentication.jwt.token.JwtToken;
import solvas.authentication.jwt.token.Scopes;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Factory to create access tokens and refresh tokens
 */
@Component
public class JwtTokenFactory {
    private JwtSettings settings;

    /**
     * Create instance
     * @param settings The Jwt Settings
     */
    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }


    /**
     * Create an access token from the userContent
     * @param userContext UserContext containing principal and roles
     * @return AccessToken
     */
    public AccessToken createAccessJwtToken(UserDetails userContext) {
        validateUserDetails(userContext);

        Claims claims = buildClaims(
                userContext,
                userContext.getAuthorities()
                        .stream()
                        .map(Object::toString)
                        .collect(Collectors.toList())
        );


        String token = buildToken(claims, settings.getTokenExpirationTime()).compact();
        return new AccessToken(token, claims);
    }


    /**
     * Create a refresh token from the userContent
     * @param userContext UserContext containing principal and roles
     * @return RefreshToken
     */
    public JwtToken createRefreshToken(UserDetails userContext) {
        validateUserDetails(userContext);

        Claims claims = buildClaims(userContext,
                Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));

        String token = buildToken(claims, settings.getRefreshTokenExpTime())
                .setId(UUID.randomUUID().toString())
                .compact();

        return new AccessToken(token, claims);
    }

    protected JwtBuilder buildToken(Claims claims, int duration) {
        DateTime currentTime = new DateTime();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentTime.toDate())
                .setIssuer(settings.getTokenIssuer())
                .setExpiration(currentTime.plusMinutes(duration).toDate())
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey());
    }

    protected Claims buildClaims(UserDetails userDetails, Collection<String> scopes) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("scopes", scopes);
        return claims;
    }

    /**
     * @param userDetails UserDetails to validate
     * @throws IllegalArgumentException if atleast one validation failed
     */
    protected void validateUserDetails(UserDetails userDetails) {
        if (StringUtils.isBlank(userDetails.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }
    }
}