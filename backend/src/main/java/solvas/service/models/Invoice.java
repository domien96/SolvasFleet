package solvas.service.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Models an invoice
 * Created by domien on 15/04/2017.
 */
public class Invoice extends Model {

    private BigDecimal amount; // in cents
    private Date startDate;
    private Date endDate;
    private InvoiceType type;
    private boolean paid;

    private Fleet fleet;

    public Invoice() {
    } // Hibernate wants a no-arg constructor

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public InvoiceType getType() {
        return type;
    }

    public void setType(InvoiceType type) {
        this.type = type;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
