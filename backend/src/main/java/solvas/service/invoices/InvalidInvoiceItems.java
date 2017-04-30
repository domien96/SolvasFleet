package solvas.service.invoices;

public class InvalidInvoiceItems extends RuntimeException {
    public InvalidInvoiceItems(String msg) {
        super(msg);
    }
}
