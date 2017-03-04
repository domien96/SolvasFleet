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

    private Timestamp createdAt; //move to Model?
    private Timestamp updatedAt; //Calculate in java

    private Timestamp startDate;
    private Timestamp endDate;
    private String url; // calculate on th fly?

    public Role(Company company, String function, User user,
                Timestamp startDate, Timestamp endDate, String url) {
        this.company = company;
        this.function = function;
        this.user = user;
        Timestamp currentTime= Timestamp.valueOf(LocalDateTime.now());
        this.createdAt = currentTime; //problems http://stackoverflow.com/questions/2635046/set-creation-and-update-time-with-hibernate-in-xml-mappings
        this.updatedAt = currentTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.url = url;
    }

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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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
