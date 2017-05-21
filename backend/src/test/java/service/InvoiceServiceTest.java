package service;

import org.junit.Before;
import org.mockito.Mock;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.InvoiceDao;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.AbstractService;
import solvas.service.InvoiceService;
import solvas.service.invoices.InvoiceCorrector;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.InvoiceMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Invoice;

import static org.mockito.Mockito.when;

public class InvoiceServiceTest extends AbstractServiceTest<Invoice,ApiInvoice> {

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private InvoiceDao invoiceDao;
    @Mock
    private InvoiceCorrector invoiceCorrector;

    @Mock
    private InvoiceMapper invoiceMapper;

    @Before
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getInvoiceDao()).thenReturn(invoiceDao);
    }

    public InvoiceServiceTest() {
        super(Invoice.class, ApiInvoice.class);
    }


    @Override
    protected AbstractService<Invoice, ApiInvoice> getService() {
        return new InvoiceService(daoContextMock, invoiceMapper, invoiceCorrector);
    }

    @Override
    protected Dao<Invoice> getDaoMock() {
        return invoiceDao;
    }

    @Override
    protected AbstractMapper<Invoice, ApiInvoice> getMapperMock() {
        return invoiceMapper;
    }
}
