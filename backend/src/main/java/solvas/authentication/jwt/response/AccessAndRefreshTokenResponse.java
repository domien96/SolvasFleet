package solvas.authentication.jwt.response;

public class AccessAndRefreshTokenResponse implements TokenResponse {
    private final TokenResponse accessToken;
    private final TokenResponse refreshToken;

    AccessAndRefreshTokenResponse(AccessTokenResponse accessToken, RefreshTokenResponse refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public TokenResponse getAccessToken() {
        return accessToken;
    }

    public TokenResponse getRefreshToken() {
        return refreshToken;
    }
}
