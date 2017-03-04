package solvas.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by david on 3/1/17.
 * @author steven
 */
public class Company extends Model {
    private String name;
    private String vat_number; //https://en.wikipedia.org/wiki/VAT_identification_number
    private String phone_number;
    private String address;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String url;

    public Company(String name, String vat_number, String phone_number, String address, String url) {
        this.name = name;
        this.vat_number = vat_number;
        this.phone_number = phone_number;
        this.address = address;
        Timestamp current_time= Timestamp.valueOf(LocalDateTime.now());
        this.created_at = current_time; //problems http://stackoverflow.com/questions/2635046/set-creation-and-update-time-with-hibernate-in-xml-mappings
        this.updated_at = current_time; //Should be updated by db
        this.url = url;


    }

    protected Company() {
    } // Hibernate wants a no-arg constructor

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVat_number() {
        return vat_number;
    }

    public void setVat_number(String vat_number) {
        this.vat_number = vat_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Company update(Company input){
        if (getId()==input.getId()) { //check variables for null
            this.name = input.name;
            this.vat_number = input.vat_number;
            this.phone_number = input.phone_number;
            this.address = input.address;
            Timestamp current_time = Timestamp.valueOf(LocalDateTime.now());
            this.updated_at = current_time; //Should be updated by db
            this.url = input.url;
            return this;
        } else return null; //replace by error
    }
}
