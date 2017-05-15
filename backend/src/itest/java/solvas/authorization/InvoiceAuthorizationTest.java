package solvas.authorization;

import solvas.rest.RestTestFixtures;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.api.models.ApiModel;

import java.time.LocalDateTime;

public class InvoiceAuthorizationTest extends AbstractAuthorizationTest {
    @Override
    public String getUrl() {
        return RestTestFixtures.INVOICE_ROOT_URL;
    }

    @Override
    public ApiModel getModel() {
        ApiInvoice invoice = new ApiInvoice();
        invoice.setId(1);
        invoice.setFleet(1);
        invoice.setPaid(false);
        invoice.setTotalAmount(100);
        invoice.setType("billing");
        invoice.setEndDate(LocalDateTime.now().plusDays(100));
        invoice.setStartDate(LocalDateTime.now());
        return invoice;
    }


    /**
     * Invoices can only be created/updated in the backend
     */

    @Override
    public void userCanPutModel() {}

    @Override
    public void userCantPutModel() {}

    @Override
    public void userCanPostModel() {}

    @Override
    public void userCantPostModel() {}

    @Override
    public void userCantDeleteModel() {}

    @Override
    public void userCanDeleteModel() {}
}
