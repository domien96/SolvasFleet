package solvas.service.models;



import java.time.LocalDateTime;
import java.util.Set;

/**
 * Models subscription of a vehicle to a (sub)fleet
 * @author domien on 4/03/2017.
 */
public class FleetSubscription extends Model {

    /**
     * Date when the vehicle has been part of the fleet.
     */
    private LocalDateTime startDate;

    /**
     * The vehicle belongs to the fleet until this date.
     */
    private LocalDateTime endDate;

    /**
     * The vehicle which is linked with the fleet.
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
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
        LocalDateTime now = LocalDateTime.now();
        return (startDate.isBefore(now) || startDate.isEqual(now)) && (endDate == null || endDate.isAfter(now));
    }

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }
}
