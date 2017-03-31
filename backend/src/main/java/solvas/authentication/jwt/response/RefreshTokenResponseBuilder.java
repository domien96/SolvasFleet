package solvas.authentication.jwt.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authentication.jwt.JwtTokenFactory;
import solvas.authentication.jwt.token.JwtToken;
import solvas.authentication.user.UserContext;

/**
 * Builder to create a response containing a refresh token
 */
@Component
public class RefreshTokenResponseBuilder implements TokenResponseBuilder {
    private JwtTokenFactory tokenFactory;

    /**
     * @param tokenFactory Factory used to create the refresh token from the user context
     */
    @Autowired
    public RefreshTokenResponseBuilder(JwtTokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @Override
    public RefreshTokenResponse build(UserContext userContext) {
        JwtToken token = tokenFactory.createRefreshToken(userContext);
        return new RefreshTokenResponse(token);
    }
}
