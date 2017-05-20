package solvas.rest;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.InvoiceDao;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.InvoiceRestController;
import solvas.service.AbstractService;
import solvas.service.InvoiceService;
import solvas.service.models.Invoice;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the InvoiceRestController
 * It checks HTTP responses
 */
public class InvoiceRestControllerTest extends AbstractBasicRestControllerTest<Invoice,ApiInvoice> {

    @Mock
    private InvoiceService service;

    @Mock
    private InvoiceDao dao;

    /**
     * InvoiceRestControllerTest constructor
     */
    public InvoiceRestControllerTest() {
        super(ApiInvoice.class);
    }


    AbstractRestController getController() {
        return new InvoiceRestController(service,dao);
    }

    @Override
    protected AbstractService<Invoice, ApiInvoice> getService() {
        return service;
    }

    /**
     * Test: obtaining an invoice with specified id
     */
    @Test
    public void getInvoiceById() throws Exception {
        when(getService().getById(anyInt())).thenReturn(getTestModel());
        getMockMvc()
                .perform(get(RestTestFixtures.INVOICE_ID_URL))
                .andExpect(status().isOk())
                .andExpect(content().json(getTestJson()));
    }

    /**
     * Test: NotFound response when invoice doesn't exist
     */
    @Test
    public void getInvoiceByIdNotFound() throws Exception {
        when(getService().getById(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc()
                .perform(get(RestTestFixtures.INVOICE_ID_URL))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: getting all invoices
     */
    @Test
    public void getInvoices() throws Exception {
        when(getService().findAll(any(),any())).thenReturn(new PageImpl(getTestModelList()));
        getMockMvc()
                .perform(get(RestTestFixtures.INVOICE_ROOT_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", Matchers.hasToString(getObjectMapper().writeValueAsString(getTestModelList()))));
    }

    /**
     * Test: obtaining the current invoice of a fleet
     */
    @Test
    public void getCurrentInvoice() throws Exception {
        when(service.findActiveInvoiceByType(anyInt(),any())).thenReturn(getTestModel());
        getMockMvc()
                .perform(get(RestTestFixtures.INVOICE_CURRENT_URL).param("type","billing"))
                .andExpect(status().isOk())
                .andExpect(content().json(getTestJson()));
    }

    /**
     * Test: BadRequest response when trying to obtain current invoice with no type
     */
    @Test
    public void getCurrentInvoiceNoType() throws Exception {
        getMockMvc()
                .perform(get(RestTestFixtures.INVOICE_CURRENT_URL))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test: Obtaining the pdf of the current invoice of a fleet (can't test content)
     */
    @Test
    public void getCurrentPdfByFleetId() throws Exception {
        when(service.findCurrentInvoice(anyInt())).thenReturn(random(Invoice.class));
        getMockMvc()
                .perform(get(RestTestFixtures.INVOICE_CURRENT_PDF))
                .andExpect(status().isOk());
    }

    /**
     * Test: NotFound response when entity doesn't exist
     */
    @Test
    public void getCurrentPdfByFleetIdNotFound() throws Exception {
        when(service.findCurrentInvoice(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc()
                .perform(get(RestTestFixtures.INVOICE_CURRENT_PDF))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: Obtaining the pdf of an invoice with specified id (can't test content)
     */
    @Test
    public void getInvoiceByIdPdf() throws Exception {
        when(dao.find(anyInt())).thenReturn(random(Invoice.class));
        getMockMvc()
                .perform(get(RestTestFixtures.INVOICE_PDF_URL))
                .andExpect(status().isOk());
    }

    /**
     * Test: NotFound response when entity doesn't exist
     */
    @Test
    public void getInvoiceByIdPdfNotFound() throws Exception {
        when(dao.find(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc()
                .perform(get(RestTestFixtures.INVOICE_PDF_URL))
                .andExpect(status().isNotFound());
    }
}
