package solvas.service.models;

import java.time.LocalDateTime;

/**
 * Models a contract
 * A contract has a type, a insuranceCompany a Vehicle(fleet sub), a franchise and a premium
 * @author Sjabasti.
 */
public class Contract extends Model {
    private FleetSubscription fleetSubscription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int franchise;
    private int premium;
    private Company company;
    private InsuranceType insuranceType;

    public Contract() {
    } // Hibernate wants a no-arg constructor

    public FleetSubscription getFleetSubscription() {
        return fleetSubscription;
    }

    public void setFleetSubscription(FleetSubscription fleetSubscription) {
        this.fleetSubscription = fleetSubscription;
    }

     public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getFranchise() {
        return franchise;
    }

    public void setFranchise(int franchise) {
        this.franchise = franchise;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
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
