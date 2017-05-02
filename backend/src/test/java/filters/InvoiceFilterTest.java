package filters;

import solvas.rest.query.ArchiveFilter;
import solvas.rest.query.InvoiceFilter;
import solvas.service.models.Invoice;

public class InvoiceFilterTest extends AbstractFilterTest<Invoice> {
    private InvoiceFilter invoiceFilter = new InvoiceFilter();

    @Override
    ArchiveFilter<Invoice> getFilterWithCorrectParameters() {
        invoiceFilter.setFleet(10);
        return invoiceFilter;
    }

    @Override
    ArchiveFilter<Invoice> getFilterWithBadParameters() {
        return invoiceFilter;
    }

    @Override
    int parameterSize() {
        return 2;
    }
}
