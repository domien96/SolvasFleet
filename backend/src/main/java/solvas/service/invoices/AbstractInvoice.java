package solvas.service.invoices;

import solvas.service.models.Invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Base extended invoice. This extended invoices wraps a normal {@link Invoice}. This class will calculate the
 * total for the invoice, and update the contained invoice.
 *
 * @param <E> The type of content in the invoice.
 *
 * @author Niko Strijbol
 */
public abstract class AbstractInvoice<E extends Cost> {

    private final Invoice invoice;

    private List<E> costs = new ArrayList<>();

    private BigDecimal total = BigDecimal.ZERO;

    /**
     * @param invoice The base invoice.
     */
    public AbstractInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /**
     * @return The base invoice.
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * @return The total of this cost.
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Add costs to the invoice. This will also update the total cost on the contained collection.
     *
     * @param collection Collection of costs.
     */
    public void addCosts(Collection<? extends E> collection) {
        this.costs.addAll(collection);
        for (E item: collection) {

            total = total.add(item.getTotal());
        }
        invoice.setAmount(total);
    }

    /**
     * @return The costs.
     */
    public Collection<E> getCosts() {
        return costs;
    }
}