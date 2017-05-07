package solvas.service.invoices;

/**
 * Exception throw when we can't correct invoices because of invalid items in the past
 * This likely indicates a bug and should be examined with high priority
 */
public class InvalidInvoiceItems extends RuntimeException {
    /**
     * Create instance
     * @param msg Exception message
     */
    public InvalidInvoiceItems(String msg) {
        super(msg);
    }
}
