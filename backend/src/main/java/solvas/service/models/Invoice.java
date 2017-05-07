package solvas.service.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * Models an invoice
 * Created by domien on 15/04/2017.
 */
public class Invoice extends Model {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private InvoiceType type;
    private boolean paid;
    private Set<InvoiceItem> items;

    private Fleet fleet;

    public Invoice() {
    } // Hibernate wants a no-arg constructor

    public Fleet getFleet() {
        return fleet;
    }

    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    /**
     * Get sum of amount of all associated items
     * @return The amount
     */
    public BigDecimal getAmount() {
        return items.stream().map(InvoiceItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
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

    public Set<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(Set<InvoiceItem> items) {
        this.items = items;
    }
}
