package solvas.service.models;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Models a role
 * Users can have different roles, and each role comes with it's own permissions
 * @author steve on 04/03/2017.
 */
public class Role extends Model {
    private String function; // change to ?

    private LocalDateTime startDate; //todo check if it should be replaced by localdate
    private LocalDateTime endDate;

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
}
