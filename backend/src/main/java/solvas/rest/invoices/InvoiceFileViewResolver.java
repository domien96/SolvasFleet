package solvas.rest.invoices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import solvas.rest.invoices.pdf.BillingPdfView;
import solvas.rest.invoices.pdf.PaymentPdfView;
import solvas.service.models.Invoice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Resolve the views for the PDF's.
 */
@Component
public class InvoiceFileViewResolver implements ViewResolver {

    public static final String PAYMENT_INVOICE_PDF_VIEW = PaymentPdfView.class.getCanonicalName();
    public static final String BILLING_INVOICE_PDF_VIEW = BillingPdfView.class.getCanonicalName();

    private Map<String, View> views = new HashMap<>();

    /**
     * @param invoicePdfView autowired
     * @param billingPdfView autowired
     */
    @Autowired
    public InvoiceFileViewResolver(PaymentPdfView invoicePdfView, BillingPdfView billingPdfView) {
        views.put(PAYMENT_INVOICE_PDF_VIEW, invoicePdfView);
        views.put(BILLING_INVOICE_PDF_VIEW, billingPdfView);
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return views.get(viewName);
    }

    /**
     * Get the view name for an invoice. This will check the type and return the name of the corresponding view.
     *
     * @param invoice The invoice whose type we check.
     *
     * @return The name of the view.
     */
    public static String getViewName(Invoice invoice) {
        switch (invoice.getType()) {
            case BILLING:
                return BILLING_INVOICE_PDF_VIEW;
            case PAYMENT:
                return PAYMENT_INVOICE_PDF_VIEW;
            default:
                throw new RuntimeException("Non-exhaustive enum switch.");
        }
    }
}
