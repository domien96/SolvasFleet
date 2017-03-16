package solvas.rest.api.models;

/**
 * Created by steve on 11/03/2017.
 */
public class ApiVehicle extends ApiModel{
    private String licensePlate;
    private String vin;
    private String model;
    private String type;
    private int mileage;
    private int year;
    private int leasingCompany;
    private int value=0;
    private int fleet;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
