package solvas.models;

import java.sql.Timestamp;

/**
 * Created by domien on 04/03/2017.
 */
public class Fleet extends Model {
    private Company company;
    private String name;

    private String url;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
