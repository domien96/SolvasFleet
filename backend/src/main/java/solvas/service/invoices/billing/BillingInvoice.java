package solvas.service.invoices.billing;

import solvas.service.invoices.AbstractInvoice;
import solvas.service.models.Invoice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An extended billing invoice, permitting better pdf's, since we have more data.
 *
 * @author Niko Strijbol
 */
public class BillingInvoice extends AbstractInvoice<Correction> {

    private List<Invoice> payments;

    /**
     * @param invoice The base invoice.
     */
    public BillingInvoice(Invoice invoice) {
        super(invoice);
    }

    @Override
    public void addCosts(Collection<? extends Correction> collection) {
        for (Correction correction: collection) {
            correction.setTotalPeriod(getInvoice());
        }
        super.addCosts(collection);
    }

    /**
     * Set the payment invoices for this billing invoice.
     *
     * @param collection Invoices.
     */
    public void setPayments(Collection<Invoice> collection) {
        this.payments = new ArrayList<>(collection);
    }

    /**
     * @return The payments.
     */
    public List<Invoice> getPayments() {
        return payments;
    }
}