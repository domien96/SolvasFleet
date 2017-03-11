package solvas.models;


import java.util.Set;

/**
 * Models a Company
 * @author david
 * @author steven
 */
public class Company extends Model {
    private String name;
    private String vatNumber; //https://en.wikipedia.org/wiki/VAT_identification_number
    private String phoneNumber;
    private String address;
    private String url;

    /**
     * These users represent this company.
     * Remark: this is a subset of the set of all employees!
     */
    private Set<User> representatives;


    public Company() {
    } // Hibernate wants a no-arg constructor

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set getRepresentatives() {
        return representatives; // todo: does this need a defensive copy or not?
    }

    public void setRepresentatives(Set representatives) {
        this.representatives = representatives;
    }
}
