package solvas.authentication.jwt.response;

import solvas.authentication.jwt.token.JwtToken;

import java.util.HashMap;
import java.util.Map;

public class AccessAndRefreshTokenResponse implements TokenResponse {
    private final Map<String, Object> accessToken;
    private final Map<String, Object> refreshToken;

    public AccessAndRefreshTokenResponse(JwtToken accessToken, JwtToken refreshToken) {
        this.accessToken = new HashMap<String, Object>() {{
            put("token", accessToken.getToken());
            put("claims", accessToken.getClaims());
        }};
        this.refreshToken = new HashMap<String, Object>() {{
            put("token", refreshToken.getToken());
            put("claims", refreshToken.getClaims());
        }};
    }
}
