package solvas.rest.api.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Schema for a Invoice as defined in the api
 * @author Steven Bastiaens
 */
public class ApiInvoice extends ApiModel {

    @NotNull
    //required
    private LocalDateTime endDate;

    @NotNull
    //required
    private LocalDateTime startDate;

    @NotNull
    //optional
    private boolean paid;

    @Min(value = 1)
    //required
    private int fleet;

    @Min(value = 0)
    //optional, in cents
    private long totalAmount;

    @Min(value = 1)
    //required
    private String type;


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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getFleet() {
        return fleet;
    }

    public void setFleet(int fleet) {
        this.fleet = fleet;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
