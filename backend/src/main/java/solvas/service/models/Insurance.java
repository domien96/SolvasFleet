package solvas.service.models;

/**
 * Created by steve on 31/03/2017.
 */
public class Insurance extends Model {
    private long riskPremium;
    private long applicableExemption;
    private Company company;
    private InsuranceType insuranceType;

    public Insurance() {
    } // Hibernate wants a no-arg constructor

    public long getRiskPremium() {
        return riskPremium;
    }

    public void setRiskPremium(long riskPremium) {
        this.riskPremium = riskPremium;
    }

    public long getApplicableExemption() {
        return applicableExemption;
    }

    public void setApplicableExemption(long applicableExemption) {
        this.applicableExemption = applicableExemption;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }
}
