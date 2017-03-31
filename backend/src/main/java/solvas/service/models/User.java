package solvas.service.models;

import java.util.Map;
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

    /**
     * Companies this user represents.
     */
    private Set<Company> companies;

    private Map<Company, Set<Role>> roles;

    public User() {
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

    public Set<Company> getCompanies() {
        return companies; // todo: does this need a defensive copy or not?
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Map<Company, Set<Role>>  getRoles() {
        return roles;
    }

    public void setRoles(Map<Company, Set<Role>> roles) {
        this.roles = roles;
    }
}
