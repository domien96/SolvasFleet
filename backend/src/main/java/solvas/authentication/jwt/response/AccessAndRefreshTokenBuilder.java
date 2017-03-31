package solvas.authentication.jwt.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authentication.user.UserContext;

@Component
public class AccessAndRefreshTokenBuilder implements TokenResponseBuilder {
    private final AccessTokenResponseBuilder accessTokenResponseBuilder;
    private final RefreshTokenResponseBuilder refreshTokenResponseBuilder;

    @Autowired
    public AccessAndRefreshTokenBuilder(
            AccessTokenResponseBuilder accessTokenResponseBuilder,
            RefreshTokenResponseBuilder refreshTokenResponseBuilder) {
        this.accessTokenResponseBuilder = accessTokenResponseBuilder;
        this.refreshTokenResponseBuilder = refreshTokenResponseBuilder;
    }


    @Override
    public TokenResponse build(UserContext userContext) {
        AccessTokenResponse accessToken = accessTokenResponseBuilder.build(userContext);
        RefreshTokenResponse refreshToken = refreshTokenResponseBuilder.build(userContext);
        return new AccessAndRefreshTokenResponse(accessToken, refreshToken);
    }
}
