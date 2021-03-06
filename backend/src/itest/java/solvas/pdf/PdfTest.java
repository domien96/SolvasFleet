package solvas.pdf;

import com.itextpdf.text.DocumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import solvas.Application;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.TestConfig;
import solvas.persistence.hibernate.HibernateConfig;
import solvas.rest.RestTestFixtures;
import solvas.rest.controller.InvoiceRestController;
import solvas.rest.controller.VehicleRestController;
import solvas.rest.greencard.GreenCardViewResolver;
import solvas.rest.greencard.pdf.GreenCardPdfView;
import solvas.rest.invoices.InvoiceFileViewResolver;
import solvas.rest.invoices.pdf.BillingPdfView;
import solvas.rest.invoices.pdf.PaymentPdfView;
import solvas.service.InvoiceService;
import solvas.service.VehicleService;
import solvas.service.invoices.InvoiceCorrector;

import java.io.IOException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ComponentScan//("solvas")
@ContextConfiguration(classes = {
        Application.class,
        TestConfig.class,
})
/**
 * Test if the generation of pdf works
 */
public class PdfTest {
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private DaoContext daoContext;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private InvoiceCorrector invoiceCorrector;

    private MockMvc getMockMvc() throws IOException, DocumentException {
        ViewResolver greenCardViewResolver = new GreenCardViewResolver(new GreenCardPdfView(daoContext));
        ViewResolver invoicePdfViewResolver = new InvoiceFileViewResolver(new PaymentPdfView(invoiceCorrector), new BillingPdfView(invoiceCorrector));
        return MockMvcBuilders
                .standaloneSetup(new VehicleRestController(vehicleService, null),
                        new InvoiceRestController(invoiceService))
                .setViewResolvers(greenCardViewResolver, invoicePdfViewResolver)
                .build();
    }

    /**
     * Can we generate greencards?
     * @throws Exception Thrown if test fails
     */
    @Test
    public void greenCardDoesNotError() throws Exception {
        getMockMvc().perform(get(RestTestFixtures.VEHICLE_GREENCARD_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * Can we generate payment pdf?
     * @throws Exception Thrown if test fails
     */
    @Test
    public void paymentPdfDoesNotError() throws Exception {
        getMockMvc().perform(get(RestTestFixtures.INVOICE_PDF_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * Can we generate billing pdf?
     * @throws Exception Thrown if test fails
     */
    @Test
    public void billingPdfDoesNotError() throws Exception {
        getMockMvc().perform(get(RestTestFixtures.BILLING_INVOICE_PDF_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful());
    }
}
