package solvas.service.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Joining relation between a user, a company and a role
 */
public class Function extends Model implements Serializable {

    /**
     * The user which this function is given to.
     */
    private User user;

    /**
     * The company where this function is applied.
     */
    private Company company;

    /**
     * The role (which is a set of permissions) this function has.
     * e.g. admin, company administrative
     */
    private Role role;


    private LocalDateTime startDate; //todo check if it should be replaced by localdate
    private LocalDateTime endDate;

    public Function() { }

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

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}
