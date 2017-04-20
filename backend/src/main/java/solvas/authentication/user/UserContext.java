package solvas.authentication.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import solvas.service.models.User;

import java.util.Collection;

/**
 * Details of a user to store in JWT
 */
public class UserContext implements UserDetails {
    private final String username;
    private String password = null;
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Create instance
     * @param user User this context belongs to
     * @param authorities Authorities of the user
     */
    public UserContext(User user, Collection<? extends GrantedAuthority> authorities) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = authorities;
    }

    /**
     * This method is used when creating the context from a JWT
     * Otherwise we would need a DB request to get the password which we won't use
     *
     * @param username Username of the user
     * @param authorities Authorities of the user
     */
    public UserContext(String username,  Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
