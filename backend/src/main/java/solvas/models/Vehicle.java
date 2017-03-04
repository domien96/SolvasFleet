package solvas.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Models a vehicle
 */
public class Vehicle extends Model {
    private String licensePlate;
    private String chassisNumber; //can contain letters https://en.wikipedia.org/wiki/Vehicle_identification_number
    private String model;
    private String type; // todo: replace by a reference
    private int kilometerCount;
    private int year;
    private Company leasingCompany;
    private int value;
    private Company company;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String url;

    public Vehicle(String licensePlate, String chassisNumber, String model, String type, int kilometerCount, int year,
                   Company leasingCompany, int value, Company company, String url) {
        this.licensePlate = licensePlate;
        this.chassisNumber = chassisNumber;
        this.model = model;
        this.type = type;
        this.kilometerCount = kilometerCount;
        this.year = year;
        this.leasingCompany = leasingCompany;
        this.value = value;
        this.company = company;
        Timestamp currentTime= Timestamp.valueOf(LocalDateTime.now());
        this.createdAt = currentTime; //problems http://stackoverflow.com/questions/2635046/set-creation-and-update-time-with-hibernate-in-xml-mappings
        this.updatedAt = currentTime;
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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
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

    public int getKilometerCount() {
        return kilometerCount;
    }

    public void setKilometerCount(int kilometerCount) {
        this.kilometerCount = kilometerCount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Company getLeasingCompany() {
        return leasingCompany;
    }

    public void setLeasingCompany(Company leasingCompany) {
        this.leasingCompany = leasingCompany;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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
}
