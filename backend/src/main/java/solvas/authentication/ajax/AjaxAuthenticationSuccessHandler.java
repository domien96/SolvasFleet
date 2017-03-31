package solvas.authentication.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import solvas.authentication.jwt.response.AccessAndRefreshTokenResponseBuilder;
import solvas.authentication.user.UserContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Handler that is called when AJAX authentication succeeds
 */
@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper mapper;
    private final AccessAndRefreshTokenResponseBuilder accessAndRefreshTokenBuilder;

    /**
     * Create handler
     *
     * @param mapper       Mapper to convert objects to JSON
     * @param accessAndRefreshTokenBuilder Builder to generate the JWT response
     */
    @Autowired
    public AjaxAuthenticationSuccessHandler(final ObjectMapper mapper, final AccessAndRefreshTokenResponseBuilder accessAndRefreshTokenBuilder) {
        this.mapper = mapper;
        this.accessAndRefreshTokenBuilder = accessAndRefreshTokenBuilder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserContext user = (UserContext) authentication.getPrincipal();

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), accessAndRefreshTokenBuilder.build(user));

        clearAuthenticationAttributes(request);
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}