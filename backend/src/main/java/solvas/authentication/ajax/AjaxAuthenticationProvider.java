package solvas.authentication.ajax;

import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import solvas.authentication.exceptions.UserNotFoundException;
import solvas.authentication.user.SolvasUserDetailsService;
import solvas.authentication.user.UserContext;
import solvas.persistence.api.DaoContext;
import solvas.service.models.Permission;
import solvas.service.models.Role;
import solvas.service.models.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {
    private final BCryptPasswordEncoder encoder;
    private final DaoContext daoContext;
    private final SolvasUserDetailsService userService;


    @Autowired
    public AjaxAuthenticationProvider(final DaoContext daoContext, final BCryptPasswordEncoder encoder, SolvasUserDetailsService userService) {
        this.daoContext = daoContext;
        this.encoder = encoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String principal = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = daoContext.getUserDao().getByEmail(principal);
        if (user == null) {
            throw new UserNotFoundException(principal);
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        UserDetails userContext = userService.loadUserByModel(user);

        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}