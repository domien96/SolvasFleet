package solvas.authentication.cas.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import solvas.authentication.UserContext;

import java.util.HashSet;

public class SolvasUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserContext(username, new HashSet<GrantedAuthority>() {{
            // TODO read permissions from database
            add(new SimpleGrantedAuthority("TODO"));
        }});
    }
}
