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

    /**
     * Check if a subscription is active.
     *
     * An active subscription is defined as a subscription for which the start date lies before or on given date,
     * and there is no end date or the end date is after the given date.
     *
     * This means: active => startDate <= date1 && (endDate == null || endDate > date2)
     * Meaning this method returns subscriptions in the interval [date1, date2[
     *
     * In this method, date1 = start and date2 = end.
     *
     * We define the end date as exclusive, to prevent two subscriptions to be active in the same moment.
     * Consider two subscriptions, one that ends on moment A and another that begins on moment A. If the end date
     * is not exclusive, this means both subscriptions will be active on moment A, which is of course not what we want.
     *
     * @return True if the subscription is currently active, false otherwise.
     */
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
