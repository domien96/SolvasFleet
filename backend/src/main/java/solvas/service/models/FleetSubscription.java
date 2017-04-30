package solvas.service.models;



import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

/**
 * Models subscription of a vehicle to a (sub)fleet
 * @author domien on 4/03/2017.
 */
public class FleetSubscription extends Model {
    private LocalDate startDate;
    private LocalDate endDate;
    private Vehicle vehicle;
    private Fleet fleet;

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
