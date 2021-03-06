package solvas.rest.api.models;

import org.hibernate.validator.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

/**
 * Api Model in the API layer
 */
public class ApiRole extends ApiModel {
    @NotBlank
    private String name;

    private int user;

    private Set<String> permissions = new HashSet<>();

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
