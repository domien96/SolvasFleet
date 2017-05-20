package solvas.service.models;


import java.util.Set;

/**
 * Models a Company.
 *
 * @author david
 * @author steven
 */
public class Company extends Model {
    /**
     * Name of the Company
     */
    private String name;

    /**
     * Unique identification of a company.
     * More information : https://en.wikipedia.org/wiki/VAT_identification_number
     */
    private String vatNumber;

    private String phoneNumber;
    private String addressCountry;
    private String addressCity;
    private String addressStreet;
    private String addressHouseNumber;
    private String addressPostalCode;

    /**
     * Type of the company.
     * @see CompanyType
     */
    private CompanyType type;

    /**
     * These users represent this company.
     * Remark: this is a subset of the set of all employees!
     */
    private Set<User> representatives;

    public Company() {
    } // Hibernate wants a no-arg constructor

    public String getAddressCountry() {
        return addressCountry;
    }

    public void setAddressCountry(String addressCountry) {
        this.addressCountry = addressCountry;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressHouseNumber() {
        return addressHouseNumber;
    }

    public void setAddressHouseNumber(String addressHouseNumber) {
        this.addressHouseNumber = addressHouseNumber;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

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

    public Set getRepresentatives() {
        return representatives; // todo: does this need a defensive copy or not?
    }

    public void setRepresentatives(Set representatives) {
        this.representatives = representatives;
    }

    public CompanyType getType() {
        return type;
    }

    public void setType(CompanyType type) {
        this.type = type;
    }
}
