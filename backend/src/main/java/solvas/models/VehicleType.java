package solvas.models;

/**
 * Models a VehicleType
 * Example types (non-exhaustive): Car, Truck, Motorcycle
 * @author domien on 4/03/2017.
 */
public class VehicleType extends Model {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
