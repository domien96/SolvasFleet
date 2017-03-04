package solvas.models;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by domien on 4/03/2017.
 */
public class Fleet_subscription extends Model {
    private Date startdate;
    private Date enddate;
    private Vehicle vehicle;
    private Fleet fleet;

    private Timestamp created_at;
    private Timestamp updated_at;

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
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
