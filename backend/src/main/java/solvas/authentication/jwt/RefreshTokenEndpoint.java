package solvas.authentication.jwt;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import solvas.authentication.exceptions.InvalidJwt;
import solvas.authentication.user.UserContext;
import solvas.authentication.WebSecurityConfig;
import solvas.authentication.user.SolvasUserDetailsService;
import solvas.authentication.jwt.token.JwtToken;
import solvas.authentication.jwt.token.RawAccessJwtToken;
import solvas.authentication.jwt.token.RefreshToken;

/**
 * RefreshTokenEndpoint
 *
 * Used to request new access tokens
 */
@RestController
public class RefreshTokenEndpoint {
    @Autowired private JwtTokenFactory tokenFactory;
    @Autowired private JwtSettings jwtSettings;
    @Autowired private SolvasUserDetailsService userService;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") private TokenExtractor tokenExtractor;

    /**
     * Request a new access token
     * @param request The request for a new access token
     * @return The body containing the new access token
     * @throws InvalidJwt The refresh token was invalid
     */
    @RequestMapping(value="/auth/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody JwtToken refreshToken(HttpServletRequest request) throws InvalidJwt {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey());

        String subject = refreshToken.getSubject();
        UserContext userContext = userService.loadUserByUsername(subject);

        return tokenFactory.createAccessJwtToken(userContext);
    }
}
