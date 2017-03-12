package solvas.models;



import java.time.LocalDate;

/**
 * Models a subFleet subsrciption
 * @author domien on 4/03/2017.
 */
public class FleetSubscription extends Model {
    private LocalDate startDate;
    private LocalDate endDate;
    private Vehicle vehicle;
    private SubFleet subFleet;


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


    public SubFleet getSubFleet() {
        return subFleet;
    }

    public void setSubFleet(SubFleet subFleet) {
        this.subFleet = subFleet;
    }
}
