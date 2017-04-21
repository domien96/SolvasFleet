package solvas.service.invoices;

import solvas.service.models.Invoice;

/**
 * A normal payment invoice. This is an invoice paid up front, based on the status of the fleet when the payment period
 * began.
 *
 * @author Niko Strijbol
 */
public class PaymentInvoice extends AbstractInvoice<Cost> {

    /**
     * @param invoice The base invoice.
     */
    public PaymentInvoice(Invoice invoice) {
        super(invoice);
    }
}