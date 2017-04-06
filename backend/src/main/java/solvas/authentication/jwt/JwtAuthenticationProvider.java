package solvas.authentication.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import solvas.authentication.jwt.token.RawAccessJwtToken;
import solvas.authentication.user.Authority;
import solvas.authentication.user.UserContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Authentication provider for JWT
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtSettings jwtSettings;
    private final ObjectMapper mapper;

    /**
     * @param jwtSettings Settings for the JWT service
     * @param mapper Jackson object mapper
     */
    @Autowired
    public JwtAuthenticationProvider(JwtSettings jwtSettings, ObjectMapper mapper) {
        this.jwtSettings = jwtSettings;
        this.mapper = mapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
        String subject = jwsClaims.getBody().getSubject();

        // Unfortunately the JWT library can't convert these to our custom class directly
        List<?> authorityObjects = jwsClaims.getBody().get(
                "scopes",
                List.class);

        List<Authority> authorities = authorityObjects.stream()
                .map(e -> mapper.convertValue(e, Authority.class))
                .collect(Collectors.toList());

        UserContext context = new UserContext(subject, authorities);

        return new JwtAuthenticationToken(context);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}