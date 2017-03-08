package solvas.models;


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


    protected Company() {
    } // Hibernate wants a no-arg constructor

    public Company(String name, String vatNumber, String phoneNumber, String address, String url) {
        this.name = name;
        this.vatNumber = vatNumber;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.url = url;
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

}
