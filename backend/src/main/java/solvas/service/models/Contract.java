package solvas.service.models;

import java.time.LocalDateTime;

/**
 * Created by steve on 31/03/2017.
 */
public class Contract extends Model {
    private FleetSubscription fleetSubscription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private long franchise;
    private long premium;
    private Company company;
    private String insuranceType;

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

    public long getFranchise() {
        return franchise;
    }

    public void setFranchise(long franchise) {
        this.franchise = franchise;
    }

    public long getPremium() {
        return premium;
    }

    public void setPremium(long premium) {
        this.premium = premium;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }
}
