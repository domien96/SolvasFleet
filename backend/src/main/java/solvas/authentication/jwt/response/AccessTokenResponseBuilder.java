package solvas.authentication.jwt.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authentication.jwt.JwtTokenFactory;
import solvas.authentication.jwt.token.JwtToken;
import solvas.authentication.user.UserContext;

/**
 * Builder to create a response containing an access token
 */
@Component
public class AccessTokenResponseBuilder implements TokenResponseBuilder {
    private JwtTokenFactory tokenFactory;

    /**
     * @param tokenFactory Factory used to create the access token from the user context
     */
    @Autowired
    public AccessTokenResponseBuilder(JwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @Override
    public AccessTokenResponse build(UserContext userContext) {
        JwtToken token = tokenFactory.createRefreshToken(userContext);
        return new AccessTokenResponse(token);
    }
}