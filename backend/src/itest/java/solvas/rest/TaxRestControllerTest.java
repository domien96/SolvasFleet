package solvas.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import solvas.rest.api.models.ApiTax;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.TaxRestController;
import solvas.service.AbstractService;
import solvas.service.TaxService;
import solvas.service.models.Tax;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests of the TaxRestController
 * It checks HTTP responses
 */
@RunWith(MockitoJUnitRunner.class)
public class TaxRestControllerTest extends AbstractBasicRestControllerTest<Tax,ApiTax> {

    @Mock
    private TaxService taxService;

    /**
     * TaxRestControllerTest constructor
     */
    public TaxRestControllerTest() {
        super(ApiTax.class);
    }

    @Override
    AbstractRestController getController() {
        return new TaxRestController(taxService);
    }

    @Override
    protected AbstractService<Tax, ApiTax> getService() {
        return taxService;
    }

    /**
     * Test: getting the tax with a certain vehicletype and contracttype
     */
    @Test
    public void getTax() throws Exception {
        when(taxService.findFor(anyString(),anyString())).thenReturn(getTestModel());

        getMockMvc().perform(get(RestTestFixtures.TAX_BASE_URL)).andExpect(content().json(getTestJson()));

    }

    /**
     * Test: updating the tax with a certain vehicletype and contracttype
     */
    @Test
    public void putTax() throws Exception {
        ApiTax tax = random(ApiTax.class);
        tax.setId(1);
        getMockMvc().perform(put(RestTestFixtures.TAX_BASE_URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson())).andExpect(status().isOk());
    }
}
