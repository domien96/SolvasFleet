package solvas.service.models;

import java.math.BigDecimal;

/**
 * Models a Commission
 *
 * @author david
 * @author steven
 */
public class Commission extends Model{
    private Company company;
    private VehicleType vehicleType;
    private Fleet fleet;
    private Vehicle vehicle;
    private BigDecimal value;

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
