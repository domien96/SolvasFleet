package solvas.rest.api.models;

import org.hibernate.validator.constraints.NotEmpty;
import solvas.models.validators.CompanyExists;

/**
 * @author Niko Strijbol
 */
public class ApiFleet extends ApiModel {

    @CompanyExists
    private int company;

    @NotEmpty
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