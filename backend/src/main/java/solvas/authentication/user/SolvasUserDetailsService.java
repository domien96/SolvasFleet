package solvas.authentication.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.UserDao;
import solvas.service.models.Company;
import solvas.service.models.Permission;
import solvas.service.models.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Build User details based on username
 */
@Service
public class SolvasUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    /**
     * @param userDao Dao used to find users
     */
    @Autowired
    public SolvasUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return loadUserByModel(userDao.findUnarchivedByEmail(username));
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    /**
     * Find UserDetails based on id
     * @param id String representation of the id to search for
     * @return UserDetails found
     * @throws UsernameNotFoundException User not found
     */
    public UserDetails loadUserById(String id) throws UsernameNotFoundException {
        return loadUserById(Integer.valueOf(id));
    }

    /**
     * Find UserDetails based on id
     * @param id Id to search for
     * @return UserDetails found
     * @throws UsernameNotFoundException User not found
     */
    public UserDetails loadUserById(int id) throws UsernameNotFoundException {
        try {
            return loadUserByModel(userDao.findUnarchivedById(id));
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(), e);
        }
    }

    /**
     * @param user The user to load UserDetails for
     * @return UserDetails containing authorities
     */
    public UserDetails loadUserByModel(User user) {
        // Get permissions for each company
        Map<Company, Collection<Permission>> permissionsMap = new HashMap<>();
        user.getFunctions().stream()
                .filter(f -> ! f.isArchived())
                .forEach(assignedRole -> {
            if (permissionsMap.containsKey(assignedRole.getCompany())) {
                permissionsMap.get(assignedRole.getCompany())
                        .addAll(assignedRole.getRole().getPermissions());
            } else {
                permissionsMap.put(assignedRole.getCompany(),
                        assignedRole.getRole().getPermissions());
            }
        });

        // Map permissions to authority objects
        Collection<GrantedAuthority> authorities = permissionsMap.entrySet().stream().map(entry -> {
            Company company = entry.getKey();
            Collection<Permission> permissions = entry.getValue();
            return new Authority(company, permissions);
        }).collect(Collectors.toSet());

        return new UserContext(user, authorities);
    }
}
