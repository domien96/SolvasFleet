package solvas.service.models;

/**
 * Created by steve on 31/03/2017.
 */
public class InsuranceType extends Model {
    private long standardFixedRate;
    private long standardTax;
    private long standardCommission;
    private String name;

    public InsuranceType() {
    } // Hibernate wants a no-arg constructor

    public long getStandardFixedRate() {
        return standardFixedRate;
    }

    public void setStandardFixedRate(long standardFixedRate) {
        this.standardFixedRate = standardFixedRate;
    }

    public long getStandardTax() {
        return standardTax;
    }

    public void setStandardTax(long standardTax) {
        this.standardTax = standardTax;
    }

    public long getStandardCommission() {
        return standardCommission;
    }

    public void setStandardCommission(long standardCommission) {
        this.standardCommission = standardCommission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
