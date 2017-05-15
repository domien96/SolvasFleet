package solvas.service.models;



import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

/**
 * Models subscription of a vehicle to a (sub)fleet
 * @author domien on 4/03/2017.
 */
public class FleetSubscription extends Model {

    /**
     * Date when the vehicle has been part of the fleet.
     */
    private LocalDate startDate;

    /**
     * The vehicle belongs to the fleet until this datE.
     */
    private LocalDate endDate;

    /**
     * The vehicle which is been linked with the fleet.
     */
    private Vehicle vehicle;

    /**
     * The fleet where the vehicle is subscribed to.
     */
    private Fleet fleet;

    /**
     * The set of contracts of the vehicles. A vehicle can have multiple contracts of multiple insurance types.
     * e.g. omnium, driver insurance, ..
     */
    private Set<Contract> contracts;


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public boolean isActive() {
        LocalDate now = LocalDate.now();
        return startDate.isBefore(now) &&
                endDate.isAfter(now);
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }
}
