package solvas.models;

import java.util.Set;

/**
 * Models a permission
 * @author domien on 4/03/2017.
 */
public class Permission extends Model {
    private String name;

    /**
     * The roles which contain this permission.
     */
    private Set<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
