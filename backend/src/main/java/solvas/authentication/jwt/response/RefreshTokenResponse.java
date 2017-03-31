package solvas.authentication.jwt.response;

import io.jsonwebtoken.Claims;
import solvas.authentication.jwt.token.JwtToken;

public class RefreshTokenResponse implements TokenResponse {
    private final String token;
    private final Claims claims;

    RefreshTokenResponse(JwtToken accessToken) {
        token = accessToken.getToken();
        claims = accessToken.getClaims();
    }

    public String getToken() {
        return token;
    }

    public Claims getClaims() {
        return claims;
    }
}
