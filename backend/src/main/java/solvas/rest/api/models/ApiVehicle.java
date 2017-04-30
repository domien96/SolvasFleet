package solvas.rest.api.models;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import solvas.service.models.validators.Vin;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Vehicle in the API layer
 */
public class ApiVehicle extends ApiModel {

    private static final int FIRST_VIN = 1981;

    private String licensePlate;

    @Vin
    @NotNull
    private String vin;

    @NotBlank
    private String model;

    @NotBlank
    private String type;

    @Min(value = 0)
    private int mileage;

    @Min(value = FIRST_VIN)
    private DateTime year;

    private int leasingCompany;

    @Min(value = 0)
    private int value;

    private int fleet = -1; //Use -1 as default value because 0 is removing it.

    @NotBlank
    private String brand;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
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

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int kilometerCount) {
        this.mileage = kilometerCount;
    }

    public DateTime getYear() {
        return year;
    }

    public void setYear(DateTime year) {
        this.year = year;
    }

    public int getLeasingCompany() {
        return leasingCompany;
    }

    public void setLeasingCompany(int leasingCompany) {
        this.leasingCompany = leasingCompany;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getFleet() {
        return fleet;
    }

    public void setFleet(int fleet) {
        this.fleet = fleet;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
