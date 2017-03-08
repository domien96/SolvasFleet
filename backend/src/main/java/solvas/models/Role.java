package solvas.models;

import java.time.LocalDateTime;
/**
 * Models a role
 * @author steve on 04/03/2017.
 */
public class Role extends Model {
    private Company company;
    private String function; // change to ?
    private User user;

    private LocalDateTime startDate; //todo check if it should be replaced by localdate
    private LocalDateTime endDate;
    private String url; // calculate on th fly?



    protected Role() {
    }

    public Role(Company company, String function, User user, LocalDateTime startDate, LocalDateTime endDate, String url) {
        this.company = company;
        this.function = function;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
