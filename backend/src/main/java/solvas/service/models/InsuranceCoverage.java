package solvas.service.models;

import java.time.LocalDateTime;

/**
 * Created by steve on 31/03/2017.
 */
public class InsuranceCoverage extends Model {
    private FleetSubscription fleetSubscription;
    private Insurance insurance;
    private LocalDateTime beginDate;
    private LocalDateTime endDate;

    public InsuranceCoverage() {
    } // Hibernate wants a no-arg constructor

    public FleetSubscription getFleetSubscription() {
        return fleetSubscription;
    }

    public void setFleetSubscription(FleetSubscription fleetSubscription) {
        this.fleetSubscription = fleetSubscription;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public LocalDateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
