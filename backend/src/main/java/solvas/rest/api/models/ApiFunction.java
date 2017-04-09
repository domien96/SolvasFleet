package solvas.rest.api.models;

/**
 * Created by david on 09/04/17.
 */
public class ApiFunction extends ApiModel {

    private int company;
    private int user;
    private int role;

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
