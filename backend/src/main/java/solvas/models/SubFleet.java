package solvas.models;

/**
 * Models a group of vehicles belonger to a larger group (a fleet)
 */
public class SubFleet extends Model {
    private Fleet fleet;
    private VehicleType vehicleType;

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
}
