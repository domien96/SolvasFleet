package solvas.rest.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by steve on 11/03/2017.
 */
public class ApiUser extends ApiModel {
    private String email;
    private String firstName;
    private String lastName;

    @JsonIgnore
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
