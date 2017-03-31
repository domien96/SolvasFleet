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

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if (StringUtils.isBlank(userContext.getUsername()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", userContext.getAuthorities()
                .stream().map(Object::toString).collect(Collectors.toList()));


        String token = buildToken(claims, settings.getTokenExpirationTime()).compact();
        return new AccessToken(token, claims);
    }


    /**
     * Create a refresh token from the userContent
     * @param userContext UserContext containing principal and roles
     * @return RefreshToken
     */
    public JwtToken createRefreshToken(UserDetails userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));

        String token = buildToken(claims, settings.getRefreshTokenExpTime())
                .setId(UUID.randomUUID().toString())
                .compact();

        return new AccessToken(token, claims);
    }

    private JwtBuilder buildToken(Claims claims, int duration) {
        DateTime currentTime = new DateTime();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(currentTime.toDate())
                .setIssuer(settings.getTokenIssuer())
                .setExpiration(currentTime.plusMinutes(duration).toDate())
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey());
    }
}