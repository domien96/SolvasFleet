package solvas.rest.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Schema for a contract as defined in the api
 * @author Steven Bastiaens
 */
public class ApiContract extends ApiModel{

    @NotNull
    //required
    private LocalDateTime endDate;

    @NotNull
    //required
    private LocalDateTime startDate;

    //optional
    private int franchise;

    @Min(value = 1)
    //required
    private int insuranceCompany;

    //optional
    private int premium;

    @NotNull
    //required
    private String type;

    @Min(value = 1)
    //required
    private int vehicle;


    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getFranchise() {
        return franchise;
    }

    public void setFranchise(int franchise) {
        this.franchise = franchise;
    }

    public int getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(int insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public int getPremium() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium = premium;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }
}
