package solvas.service.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Models overwritten taxes (Dutch: overschreven kosten)
 * Created by domien on 15/04/2017.
 * @deprecated will be removed, which is why this model has not been documented.
 */
@Deprecated
public class OverwrittenTaxes extends Model {

    private BigDecimal forfaitair;
    private BigDecimal taxes;
    private BigDecimal commission;
    private Date startDate;
    private Date endDate;
    private Fleet fleet;
    private InsuranceType insuranceType;

    public OverwrittenTaxes() {
    } // Hibernate wants a no-arg constructor

    public BigDecimal getForfaitair() {
        return forfaitair;
    }

    public void setForfaitair(BigDecimal forfaitair) {
        this.forfaitair = forfaitair;
    }

    public BigDecimal getTaxes() {
        return taxes;
    }

    public void setTaxes(BigDecimal taxes) {
        this.taxes = taxes;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
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
