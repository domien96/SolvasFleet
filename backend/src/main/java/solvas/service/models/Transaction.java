package solvas.service.models;

import java.util.Date;

/**
 * Models transaction costs (Dutch: overschreven kosten)
 * Created by domien on 15/04/2017.
 */
public class Transaction extends Model {

    private double forfaitair;
    private double taxes;
    private double commission;
    private Date startDate;
    private Date endDate;
    private Fleet fleet;
    private InsuranceType insuranceType;

    public Transaction() {
    } // Hibernate wants a no-arg constructor

    public double getForfaitair() {
        return forfaitair;
    }

    public void setForfaitair(double forfaitair) {
        this.forfaitair = forfaitair;
    }

    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
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

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }
}
