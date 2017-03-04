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

    private Timestamp created_at; //move to Model?
    private Timestamp updated_at; //Calculate in java

    private Timestamp start_date;
    private Timestamp end_date;
    private String url; // calculate on th fly?

    public Role(Company company, String function, User user,
                Timestamp start_date, Timestamp end_date, String url) {
        this.company = company;
        this.function = function;
        this.user = user;
        Timestamp current_time= Timestamp.valueOf(LocalDateTime.now());
        this.created_at = current_time; //problems http://stackoverflow.com/questions/2635046/set-creation-and-update-time-with-hibernate-in-xml-mappings
        this.updated_at = current_time;
        this.start_date = start_date;
        this.end_date = end_date;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getStart_date() {
        return start_date;
    }

    public void setStart_date(Timestamp start_date) {
        this.start_date = start_date;
    }

    public Timestamp getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Timestamp end_date) {
        this.end_date = end_date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
