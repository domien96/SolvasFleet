package solvas.models;

/**
 * Created by steve on 11/03/2017.
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
