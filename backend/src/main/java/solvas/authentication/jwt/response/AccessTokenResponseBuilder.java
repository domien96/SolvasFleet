package solvas.authentication.jwt.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authentication.jwt.JwtTokenFactory;
import solvas.authentication.jwt.token.AccessToken;
import solvas.authentication.jwt.token.JwtToken;
import solvas.authentication.user.UserContext;

@Component
public class AccessTokenResponseBuilder implements TokenResponseBuilder {
    private JwtTokenFactory tokenFactory;

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