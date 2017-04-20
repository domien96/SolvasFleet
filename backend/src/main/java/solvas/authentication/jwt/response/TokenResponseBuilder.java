package solvas.authentication.jwt.response;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Builder to create token responses from usercontexts
 */
public interface TokenResponseBuilder {
    /**
     *
     * @param userContext The usercontext to create a token for
     * @return The tokenresponse
     */
    TokenResponse build(UserDetails userContext);
}
