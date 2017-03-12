package solvas.models;

/**
 * Models a vehicle
 * @author steven
 */
public class Vehicle extends Model {
    private String licensePlate;
    private String chassisNumber; //can contain letters https://en.wikipedia.org/wiki/Vehicle_identification_number
    private String model;
    private VehicleType type;
    private int kilometerCount;
    private int year;
    private Company leasingCompany;
    private int value; //TODO check if used
    private final String url="/vehicles/";
    private String brand;



    public Vehicle() {
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

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
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

    public String getUrl() {
        return url+getId();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
