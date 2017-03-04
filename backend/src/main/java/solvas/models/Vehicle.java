package solvas.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Vehicle extends Model {
    private String license_plate;
    private String chassis_number; //can contain letters https://en.wikipedia.org/wiki/Vehicle_identification_number
    private String model;
    private String type;
    private int kilometer_count;
    private int year;
    private Company leasing_company;
    private int value;
    private Company company;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String url;

    public Vehicle(String license_plate, String chassis_number, String model, String type, int kilometer_count, int year,
                   Company leasing_company, int value, Company company, String url) {
        this.license_plate = license_plate;
        this.chassis_number = chassis_number;
        this.model = model;
        this.type = type;
        this.kilometer_count = kilometer_count;
        this.year = year;
        this.leasing_company = leasing_company;
        this.value = value;
        this.company = company;
        Timestamp current_time= Timestamp.valueOf(LocalDateTime.now());
        this.created_at = current_time; //problems http://stackoverflow.com/questions/2635046/set-creation-and-update-time-with-hibernate-in-xml-mappings
        this.updated_at = current_time;
        this.url = url;
    }

    protected Vehicle() {
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getChassis_number() {
        return chassis_number;
    }

    public void setChassis_number(String chassis_number) {
        this.chassis_number = chassis_number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getKilometer_count() {
        return kilometer_count;
    }

    public void setKilometer_count(int kilometer_count) {
        this.kilometer_count = kilometer_count;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Company getLeasing_company() {
        return leasing_company;
    }

    public void setLeasing_company(Company leasing_company) {
        this.leasing_company = leasing_company;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
}
