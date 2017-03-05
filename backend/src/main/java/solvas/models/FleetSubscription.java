package solvas.models;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by domien on 4/03/2017.
 */
public class FleetSubscription extends Model {
    private Date startDate;
    private Date endDate;
    private Vehicle vehicle;
    private Fleet fleet;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
