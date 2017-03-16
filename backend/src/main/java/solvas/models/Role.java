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

    /**
     * Users who have this role.
     */
    private User user;
    private Company company;

    /**
     * The set of permissions which belong to this role.
     */
    private Set<Permission> permissions;

    public Role() {
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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
