package solvas.rest.api.models;

import org.hibernate.validator.constraints.NotBlank;
import solvas.service.models.validators.StartBeforeEnd;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Api Model in the API layer
 */
public class ApiRole extends ApiModel {
    @NotBlank
    private String function;

    private int user;

    private Set<Integer> permissions = new HashSet<>();

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public Set<Integer> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Integer> permissions) {
        this.permissions = permissions;
    }
}
