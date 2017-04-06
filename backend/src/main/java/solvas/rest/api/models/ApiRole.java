package solvas.rest.api.models;

import org.hibernate.validator.constraints.NotBlank;
import solvas.service.models.validators.StartBeforeEnd;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Api Model in the API layer
 */
@StartBeforeEnd
public class ApiRole extends ApiModel {
    @NotNull
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @NotBlank
    private String function;

    private int user;

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
