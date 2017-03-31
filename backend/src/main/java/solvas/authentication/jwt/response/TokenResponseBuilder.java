package solvas.authentication.jwt.response;

import solvas.authentication.user.UserContext;

public interface TokenResponseBuilder {
    TokenResponse build(UserContext userContext);
}
