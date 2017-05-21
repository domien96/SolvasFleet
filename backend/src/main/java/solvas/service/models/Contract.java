package solvas.service.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiCommission;
import solvas.rest.query.CommissionFilter;
import solvas.service.CommissionService;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Models a contract
 * A contract has a type, a insuranceCompany a Vehicle(fleet sub), a franchise and a premium
 * A contract links a {@link Vehicle} with the insurance company.
 * @author Sjabasti.
 */
public class Contract extends Model {

    /**
     * Used to get the commission.
     */
    @Autowired
    private CommissionService commissionService;

    /**
     * The vehicle linked with this fleet subscription is insuranced with this contract.
     */
    private FleetSubscription fleetSubscription;

    /**
     * Date and time since the contract is active.
     */
    private LocalDateTime startDate;

    /**
     * Date and time of the end of this contract.
     */
    private LocalDateTime endDate;

    /**
     * Percentage of the premium which acts as a lower limit. If the damage costs is lower than the franchise,
     * the insurance company will not pay the damage.
     */
    private int franchise;

    /**
     * The cost of the contract. This is the price the owner of the vehicle has to pay to the insurance company.
     * This is a fixed amount.
     */
    private int premium;

    /**
     * The insurance company which serves the contract to the vehicle owner.
     */
    private Company company;

    /**
     * The type of insurance.
     * @see InsuranceType
     */
    private InsuranceType insuranceType;
    private Set<InvoiceItem> invoiceItems;

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

    /**
     * The netto premium. Which is the premium (= riskpremium) + the commission.
     * @return the netto premium
     */
    public long getPremium() {
        // with commission
        Vehicle vehicle = fleetSubscription.getVehicle();
        CommissionFilter filter = new CommissionFilter();
        filter.setInsuranceType(insuranceType.getName());
        filter.setVehicle(vehicle.getId());
        filter.setVehicleType(vehicle.getType().getName());
        filter.setFleet(fleetSubscription.getFleet().getId());
        filter.setCompany(company.getId());
        Page<ApiCommission> result = commissionService.findAll(new PageRequest(0,10),filter); //dummy page request
        if(!result.hasContent()) {
            return premium;
        } else {
            return premium + result.getContent().get(0).getValue();
        }
    }

    /**
     * Sets the risk premium.
     * @param premium the risk premium
     */
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

    public Set<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(Set<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }
}
