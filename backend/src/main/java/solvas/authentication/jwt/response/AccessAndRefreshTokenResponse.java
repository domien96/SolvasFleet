package solvas.authentication.jwt.response;

/**
 * Represents a response containing an access token and a refresh token
 */
public class AccessAndRefreshTokenResponse implements TokenResponse {
    private final AccessTokenResponse accessToken;
    private final RefreshTokenResponse refreshToken;

    /**
     * @param accessToken The access token part of the response
     * @param refreshToken The refresh token part of the response
     */
    AccessAndRefreshTokenResponse(AccessTokenResponse accessToken, RefreshTokenResponse refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    /**
     * @return The access token part of the response
     */
    public AccessTokenResponse getAccessToken() {
        return accessToken;
    }

    /**
     * @return The refresh token part of the response
     */
    public RefreshTokenResponse getRefreshToken() {
        return refreshToken;
    }
}
