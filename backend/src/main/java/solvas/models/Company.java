package solvas.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by david on 3/1/17.
 * @author steven
 */
public class Company extends Model {
    private String name;
    private String vatNumber; //https://en.wikipedia.org/wiki/VAT_identification_number
    private String phoneNumber;
    private String address;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String url;


    protected Company() {
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * TODO check if usefull
     * @param input TODO
     * @return TODO
     */
    public Company update(Company input){
        if (getId()==input.getId()) { //check variables for null
            this.name = input.name;
            this.vatNumber = input.vatNumber;
            this.phoneNumber = input.phoneNumber;
            this.address = input.address;
            this.updatedAt = Timestamp.valueOf(LocalDateTime.now()); //Should be updated by db
            this.url = input.url;
            return this;
        } else {return null;} //replace by error
    }
}
