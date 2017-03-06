package solvas.models;

/**
 * Created by david on 3/1/17.
 */
public class Company extends Model {
    private String name;
    private String vat;

    public Company(String name, String vat) {
        this.name = name;
        this.vat = vat;
    }

    protected Company() {} // Hibernate wants a no-arg constructor

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }
}
