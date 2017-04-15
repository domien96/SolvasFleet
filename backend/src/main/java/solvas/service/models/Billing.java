package solvas.service.models;

/**
 * Models a billing
 * Created by domien on 15/04/2017.
 */
public class Billing extends Model {

    private double amount;
    private Fleet fleet;

    public Billing() {
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
}
