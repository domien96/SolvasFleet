package solvas.authentication.jwt.response;

import io.jsonwebtoken.Claims;
import solvas.authentication.jwt.token.JwtToken;

public class AccessTokenResponse implements TokenResponse {
    private final String token;
    private final Claims claims;

    AccessTokenResponse(JwtToken accessToken) {
        token = accessToken.getToken();
        claims = accessToken.getClaims();
    }

    public Claims getClaims() {
        return claims;
    }

    public String getToken() {
        return token;
    }
}
