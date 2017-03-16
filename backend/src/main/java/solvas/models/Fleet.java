package solvas.models;

/**
 * Models a fleet
 * @author domien on 04/03/2017
 */
public class Fleet extends Model {
    private Company company;
    private String name;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
