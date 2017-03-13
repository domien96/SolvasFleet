package solvas.rest.api.models;

/**
 * @author Niko Strijbol
 */
public class ApiFleet extends ApiModel {

    private int company;
    private String name;

    public int getCompany() {
        return company;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}