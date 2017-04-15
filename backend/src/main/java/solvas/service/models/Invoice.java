package solvas.service.models;

import java.util.Date;

/**
 * Models an invoice
 * Created by domien on 15/04/2017.
 */
public class Invoice extends Model {

    private double amount;
    private Date startDate;
    private Date endDate;
    private Fleet fleet;

    public Invoice() {
    } // Hibernate wants a no-arg constructor

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

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
}
