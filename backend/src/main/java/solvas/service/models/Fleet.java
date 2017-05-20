package solvas.service.models;

import java.util.Collection;

/**
 * Models a fleet
 * A fleet is a group of vehicles.
 * @author domien on 04/03/2017
 */
public class Fleet extends Model {

    /**
     * The company who owns this fleet. (and thus all the vehicles of this fleet)
     */
    private Company company;

    /**
     * The name of the fleet.
     */
    private String name;

    /**
     * Period of facturation.
     * In months
     */
    private int facturationPeriod;

    /**
     * Period of payment.
     * In months
     */
    private int paymentPeriod;

    private Collection<FleetSubscription> subscriptions;


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFacturationPeriod() {
        return facturationPeriod;
    }

    public void setFacturationPeriod(int facturationPeriod) {
        this.facturationPeriod = facturationPeriod;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }

    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public Collection<FleetSubscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Collection<FleetSubscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
