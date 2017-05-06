package mappers;

import org.junit.Test;
import shared.AbstractSolvasTest;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.mappers.InvoiceMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Invoice;

public class InvoiceMapperTest extends AbstractSolvasTest<Invoice,ApiInvoice> {

    private InvoiceMapper mapper;

    public InvoiceMapperTest() {
        super(Invoice.class,ApiInvoice.class);
        mapper = new InvoiceMapper(getDaoContext());
    }

    @Test
    public void convertInvoiceToApiInvoice()
    {
        ApiInvoice result = mapper.convertToApiModel(getModel());

        //Compare (triviaal)
    }

    @Test
    public void convertApiInvoiceToInvoice() throws DependantEntityNotFound, EntityNotFoundException {
        //Zou normaalgezien niet mogen gebeuren, error/exception moet gethrowed worden (zie verslag meeting 9)
    }

}
