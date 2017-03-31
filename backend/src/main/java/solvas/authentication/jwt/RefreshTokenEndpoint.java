package solvas.authentication.jwt;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import solvas.authentication.WebSecurityConfig;
import solvas.authentication.exceptions.InvalidJwt;
import solvas.authentication.jwt.response.AccessAndRefreshTokenResponseBuilder;
import solvas.authentication.jwt.response.TokenResponse;
import solvas.authentication.user.UserContext;
import solvas.authentication.user.SolvasUserDetailsService;
import solvas.authentication.jwt.token.RawAccessJwtToken;
import solvas.authentication.jwt.token.RefreshToken;

/**
 * RefreshTokenEndpoint
 *
 * Used to request new access tokens
 */
@RestController
public class RefreshTokenEndpoint {
    private final AccessAndRefreshTokenResponseBuilder accessAndRefreshTokenBuilder;
    private final JwtSettings jwtSettings;
    private final SolvasUserDetailsService userService;
    private final TokenExtractor tokenExtractor;

    /**
     * Create instance
     * @param accessAndRefreshTokenBuilder Builder to create response
     * @param jwtSettings JwtSettings
     * @param userService Service to create UserContext from principal
     * @param tokenExtractor Extract token from request
     */
    @Autowired
    public RefreshTokenEndpoint(
            AccessAndRefreshTokenResponseBuilder accessAndRefreshTokenBuilder,
            JwtSettings jwtSettings,
            SolvasUserDetailsService userService,
            TokenExtractor tokenExtractor) {
        this.accessAndRefreshTokenBuilder = accessAndRefreshTokenBuilder;
        this.jwtSettings = jwtSettings;
        this.userService = userService;
        this.tokenExtractor = tokenExtractor;
    }


    /**
     * Request a new access token
     * @param request The request for a new access token
     * @return The body containing the new access token
     * @throws InvalidJwt The refresh token was invalid
     */
    @RequestMapping(value="/auth/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody TokenResponse refreshToken(HttpServletRequest request) throws InvalidJwt {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken oldRefreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey());

        String subject = oldRefreshToken.getSubject();
        UserContext user = userService.loadUserByUsername(subject);

        return accessAndRefreshTokenBuilder.build(user);
    }
}
