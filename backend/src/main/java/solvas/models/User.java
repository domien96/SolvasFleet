package solvas.models;

import java.util.Set;

/**
 * Models a User
 * @author steve on 04/03/2017.
 */
public class User extends Model {
    private String firstName;
    private String lastName;
    private String email;
    private String password; //change to non string?
    private String url;

    /**
     * Companies this user represents.
     */
    private Set<Company> companies;

    private Set<Role> roles;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set getCompanies() {
        return companies; // todo: does this need a defensive copy or not?
    }

    public void setCompanies(Set companies) {
        this.companies = companies;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
