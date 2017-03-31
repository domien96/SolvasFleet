package solvas.authentication.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import solvas.persistence.api.dao.UserDao;
import solvas.service.models.Permission;
import solvas.service.models.Role;
import solvas.service.models.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Build User details based on username
 */
@Component
public class SolvasUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    /**
     *
     * @param userDao Dao used to find users
     */
    @Autowired
    public SolvasUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByModel(userDao.getByEmail(username));
    }

    /**
     * @param user The user to load UserDetails for
     * @return UserDetails containing authorities
     */
    public UserDetails loadUserByModel(User user) {
        System.out.println(user.getRoles().isEmpty());
        Collection<GrantedAuthority> authorities = new HashSet<>();/*  user
                .getRoles().entrySet().stream()
                .map(entry ->
                        entry.getValue().stream()
                    .map(Role::getPermissions)
                    .flatMap(Collection::stream)
                    .map(Permission::getName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet())
                ).collect(Collectors.toMap(entry, ));*/
        return new UserContext(user.getEmail(), authorities);
    }
}
