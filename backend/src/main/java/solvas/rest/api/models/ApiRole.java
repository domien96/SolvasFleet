package solvas.rest.api.models;

import java.time.LocalDateTime;

/**
 * Created by steve on 11/03/2017.
 */
public class ApiRole extends ApiModel {
    private int company;
    private LocalDateTime endDate;
    private String function;
    private LocalDateTime startDate;

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

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

    private int user;
}
