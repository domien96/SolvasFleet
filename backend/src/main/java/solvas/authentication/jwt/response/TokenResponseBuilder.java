package solvas.authentication.jwt.response;

import solvas.authentication.user.UserContext;

/**
 * Builder to create token responses from usercontexts
 */
public interface TokenResponseBuilder {
    /**
     *
     * @param userContext The usercontext to create a token for
     * @return The tokenresponse
     */
    TokenResponse build(UserContext userContext);
}
