package solvas.rest.api.models;

import org.hibernate.validator.constraints.NotBlank;

/**
 * An address. Because of the difficulties involved in validating addresses, most attributes are only
 * validated on existence, not on valid content.
 *
 * TODO: evaluate http://i18napis.appspot.com/address, as it contains a lot of data.
 *
 * @author Steven Bastiaens
 * @author Niko Strijbol
 */
public class ApiAddress {

    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String houseNumber;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String street;

    public ApiAddress() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
