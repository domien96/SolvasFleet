package solvas.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by steve on 04/03/2017.
 */
public class User extends Model {
    private String firstName;
    private String lastName;
    private String email;
    private String password; //change to non string?
    private Timestamp createdAt; //move to Model?
    private Timestamp updatedAt; //move to Model?
    private String url;

    public User(String firstName, String lastName, String email, String password, String url) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        Timestamp currentTime= Timestamp.valueOf(LocalDateTime.now());
        this.createdAt = currentTime; //problems http://stackoverflow.com/questions/2635046/set-creation-and-update-time-with-hibernate-in-xml-mappings
        this.updatedAt = currentTime;
        this.url = url;
    }
    protected User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
