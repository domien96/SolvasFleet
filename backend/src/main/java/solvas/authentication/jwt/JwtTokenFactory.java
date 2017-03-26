package solvas.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authentication.UserContext;
import solvas.authentication.jwt.token.AccessJwtToken;
import solvas.authentication.jwt.token.JwtToken;
import solvas.authentication.jwt.token.Scopes;

import java.util.Collections;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtTokenFactory {
    JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }


    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("scopes", userContext.getAuthorities()
                .stream().map(Object::toString).collect(Collectors.toList()));


        String token = buildToken(claims, settings.getTokenExpirationTime()).compact();
        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserContext user) {
        if (StringUtils.isBlank(user.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("scopes", Collections.singletonList(Scopes.REFRESH_TOKEN.authority()));

        String token = buildToken(claims, settings.getRefreshTokenExpTime())
                .setId(UUID.randomUUID().toString())
                .compact();

        return new AccessJwtToken(token, claims);
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