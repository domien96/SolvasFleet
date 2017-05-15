package solvas.service.models;

import org.joda.time.DateTime;

import java.util.Set;

/**
 * Models a vehicle
 * @author steven
 */
public class Vehicle extends Model {

    /**
     * The license plate of the vehicle.
     */
    private String licensePlate;

    /**
     * The chassis number. It has a standard format.
     * More information: https://en.wikipedia.org/wiki/Vehicle_identification_number
     */
    private String chassisNumber;

    /**
     * The model of the vehicle. "Model" means something else than a model object in this context.
     */
    private String model;

    /**
     * The typpe of the vehicle.
     */
    private VehicleType type;

    /**
     * Total amount of kilometers this vehicle has already been driven with.
     */
    private int kilometerCount;

    /**
     * The build year of the vehicle.
     */
    private int year;

    /**
     * Owner company of this vehicle.
     */
    private Company leasingCompany;

    /**
     * The value of this vehicle. (How much it is worth)
     */
    private int value;

    /**
     * The brand which produced this vehicle.
     */
    private String brand;

    /**
     * The set of subscriptions (to fleets) this vehicle possessed or currently possesses.
     * Using these fleet subscription, one can retrieve all fleets where this vehicle belongs/belonged to.
     * There is at most 1 active fleet subscription at any given time. In other words, the fleet subscriptions period (start date to end date)
     * may not intersect.
     */
    private Set<FleetSubscription> fleetSubscriptions;

    public Vehicle() {
    }

    public Set<FleetSubscription> getFleetSubscriptions() {
        return fleetSubscriptions;
    }

    public void setFleetSubscriptions(Set<FleetSubscription> fleetSubscriptions) {
        this.fleetSubscriptions = fleetSubscriptions;
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


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
