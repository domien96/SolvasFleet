package solvas.service.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * Models an invoice of a fleet for a specific period.
 * Created by domien on 15/04/2017.
 */
public class Invoice extends Model {

    /**
     * The start date of the period this invoice is for.
     */
    private LocalDateTime startDate;

    /**
     * The end date of the period this invoice is for.
     */
    private LocalDateTime endDate;

    /**
     * The type of the invoice.
     * @see InvoiceType
     */
    private InvoiceType type;

    /**
     * Holds track whether the invoice is already paid or not.
     * Payment of invoices is done outside the whole application.
     */
    private boolean paid;
    private Set<InvoiceItem> items;

    /**
     * The fleet this invoice is made for.
     */
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
    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(InvoiceItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * @return Net amount of all associated items (before taxes)
     */
    public BigDecimal getNetAmount() {
        return items.stream()
                .map(InvoiceItem::getNetAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
