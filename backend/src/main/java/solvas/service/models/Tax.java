package solvas.service.models;

import java.math.BigDecimal;

/**
 * Models a Tax
 * @author Sjabasti
 */
public class Tax extends Model {
    // todo when tax branch has been merged
    private BigDecimal tax;
    private VehicleType vehicleType;
    private InsuranceType insuranceType;

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }
}
