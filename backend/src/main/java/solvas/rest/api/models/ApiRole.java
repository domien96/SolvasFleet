package solvas.rest.api.models;

import org.hibernate.validator.constraints.NotBlank;
import solvas.service.models.validators.StartBeforeEnd;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Api Model in the API layer
 */
public class ApiRole extends ApiModel {
    @NotBlank
    private String function;

    private int user;

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
}
