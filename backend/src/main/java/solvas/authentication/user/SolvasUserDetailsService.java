package solvas.authentication.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class SolvasUserDetailsService implements UserDetailsService {
    @Override
    public UserContext loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserContext(username, new HashSet<GrantedAuthority>() {{
            // TODO read permissions from database
            add(new SimpleGrantedAuthority("TODO"));
        }});
    }
}
