package solvas.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by steve on 04/03/2017.
 */
public class Role extends Model {
    private Company company;
    private String function; // change to ?
    private User user;

    private Timestamp startDate;
    private Timestamp endDate;
    private String url; // calculate on th fly?



    protected Role() {
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
