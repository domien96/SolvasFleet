package solvas.authentication.jwt.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.authentication.user.UserContext;

/**
 * Builder to create a response containing a refresh token and an access token
 */
@Component
public class AccessAndRefreshTokenResponseBuilder implements TokenResponseBuilder {
    private final AccessTokenResponseBuilder accessTokenResponseBuilder;
    private final RefreshTokenResponseBuilder refreshTokenResponseBuilder;

    /**
     * Created a builder
     * @param accessTokenResponseBuilder Builder used to create the access token
     * @param refreshTokenResponseBuilder Builder used to create the refresh token
     */
    @Autowired
    public AccessAndRefreshTokenResponseBuilder(
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
