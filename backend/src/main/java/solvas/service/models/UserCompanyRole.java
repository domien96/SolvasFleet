package solvas.service.models;

import java.io.Serializable;

/**
 * Joining relation between a user, a company and a role
 */
public class UserCompanyRole extends Model implements Serializable {

    private User user;
    private Company company;
    private Role role;


    public UserCompanyRole() { }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
