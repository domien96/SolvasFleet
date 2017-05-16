package solvas.rest;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import solvas.rest.api.models.ApiContract;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.ContractRestController;
import solvas.service.AbstractService;
import solvas.service.ContractService;
import solvas.service.models.Contract;

import java.util.List;

import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests of the ContractRestController
 * It checks HTTP responses
 */
public class ContractRestControllerTest extends AbstractRestControllerTest<Contract,ApiContract> {

    @Mock
    private ContractService service;


    /**
     * ContractRestControllerTest constructor
     */
    public ContractRestControllerTest() {
        super(ApiContract.class);
    }

    @Override
    AbstractRestController getController() {
        return new ContractRestController(service);
    }

    @Override
    protected AbstractService<Contract, ApiContract> getService() {
        return service;
    }

    @Override
    protected String getBaseUrl() {
        return RestTestFixtures.CONTRACT_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.CONTRACT_ID_URL;
    }

    /**
     * Test: listing all insurancetypes
     */
    @Test
    public void listAllTypes() throws Exception {
        List<String> list = Lists.newArrayList("a","b");
        when(service.findAllInsuranceTypes()).thenReturn(list);
        getMockMvc()
                .perform(get(RestTestFixtures.CONTRACT_TYPES))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", org.hamcrest.Matchers.hasToString(getObjectMapper().writeValueAsString(list))));
    }

    /**
     * Test: getting the contracts of a certain vehicle in a fleet from a company successfully
     */
    @Test
    public void getByCompanyFleetVehicleId() throws Exception {
        getContractsOnSpecifiedUrl(RestTestFixtures.CONTRACT_BY_COMPANY_FLEET_VEHICLE);
    }

    /**
     * Test: Getting all the contracts of a certain company
     */
    @Test
    public void getByCompanyId() throws Exception {
        getContractsOnSpecifiedUrl(RestTestFixtures.CONTRACT_BY_COMPANY_ID);
    }

    /**
     * Test method to get multiple contracts
     * @param url: The rest endpoint
     */
    private void getContractsOnSpecifiedUrl(String url) throws Exception {
        when(service.findAll(any(),any())).thenReturn(new PageImpl(getTestModelList()));
        getMockMvc().perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",org.hamcrest.Matchers.hasToString(getObjectMapper().writeValueAsString(getTestModelList()))));
    }
}
