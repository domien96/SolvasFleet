package solvas.models;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Models a role
 * @author steve on 04/03/2017.
 */
public class Role extends Model {
    private String function; // change to ?

    private LocalDateTime startDate; //todo check if it should be replaced by localdate
    private LocalDateTime endDate;
    private String url; // calculate on th fly?

    /**
     * Users who have this role.
     */
    private Set<User> users;

    /**
     * The set of permissions which belong to this role.
     */
    private Set<Permission> permissions;

    protected Role() {
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
