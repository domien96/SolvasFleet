package solvas.models;

//import java.sql.Date;

import java.time.LocalDate;

/**
 * Models a fleet subsrciption
 * @author domien on 4/03/2017.
 */
public class FleetSubscription extends Model {
    private LocalDate startDate;
    private LocalDate endDate;
    private Vehicle vehicle;
    private Fleet fleet;


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

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }
}
