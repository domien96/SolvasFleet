package solvas.rest.api.models;

import org.hibernate.validator.constraints.NotBlank;
import solvas.service.models.validators.PhoneNumber;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by steve on 11/03/2017.
 */
public class ApiCompany extends ApiModel {

    @Valid
    @NotNull
    private ApiAddress address;

    @NotBlank
    private String name;

    @NotNull
    @PhoneNumber
    private String phoneNumber;

    @NotBlank
    private String vatNumber;

    @NotBlank
    private String type;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
