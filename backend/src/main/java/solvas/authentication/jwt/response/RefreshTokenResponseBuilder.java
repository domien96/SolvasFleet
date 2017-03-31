package solvas.authentication.jwt.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authentication.jwt.JwtTokenFactory;
import solvas.authentication.jwt.token.JwtToken;
import solvas.authentication.user.UserContext;

@Component
public class RefreshTokenResponseBuilder implements TokenResponseBuilder {
    private JwtTokenFactory tokenFactory;

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
