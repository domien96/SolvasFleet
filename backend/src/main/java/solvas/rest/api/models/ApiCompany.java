package solvas.rest.api.models;

/**
 * Created by steve on 11/03/2017.
 */
public class ApiCompany extends ApiModel{
    private ApiAddress address;
    private String name;
    private String phoneNumber;
    private String vatNumber;


    public ApiAddress getAddress() {
        return address;
    }

    public void setAddress(ApiAddress address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }
}
