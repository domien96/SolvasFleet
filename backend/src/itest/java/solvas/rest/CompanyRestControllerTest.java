package solvas.rest;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.ResultActions;
import solvas.authorization.CompanyExtractor;
import solvas.service.models.Company;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.CompanyRestController;
import solvas.service.AbstractService;
import solvas.service.CompanyService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Integration tests of the CompanyRestController
 * It checks HTTP responses and calls to the CompanyDao
 */
public class CompanyRestControllerTest extends AbstractRestControllerTest<Company,ApiCompany>{


    @Mock
    private CompanyService service;
    @Mock
    private CompanyExtractor companyExtractor;


    /**
     * Constructor for specific CompanyController tests
     */
    public CompanyRestControllerTest() {
        super(ApiCompany.class);
    }


    /**
     * Match jsonmodel with ApiCompany
     * @param resultActions the resultaction that mockMvc provides.
     * @param source the company we want to compare with the json result
     */
    public void matchJsonModel(ResultActions resultActions, ApiCompany source) throws Exception {
        resultActions.andExpect(jsonPath("id").value(source.getId()))
                .andExpect(jsonPath("name").value(source.getName()))
                .andExpect(jsonPath("phoneNumber").value(source.getPhoneNumber()))
                .andExpect(jsonPath("vatNumber").value(source.getVatNumber()))
                .andExpect(jsonPath("url").value(source.getUrl()))
                .andExpect(jsonPath("address.city").value(source.getAddress().getCity()))
                .andExpect(jsonPath("address.country").value(source.getAddress().getCountry()))
                .andExpect(jsonPath("address.houseNumber").value(source.getAddress().getHouseNumber()))
                .andExpect(jsonPath("address.postalCode").value(source.getAddress().getPostalCode()))
                .andExpect(jsonPath("address.street").value(source.getAddress().getStreet()));

    }


    /**
     * @return the company rest controller
     */
    @Override
    AbstractRestController getController() {
        return new CompanyRestController(service, companyExtractor);
    }

    @Override
    ApiCompany getTestModel()
    {
        ApiCompany company = super.getTestModel();
        company.setPhoneNumber("093999084");
        return company;
    }

    @Override
    protected AbstractService<Company, ApiCompany> getService() {
        return service;
    }

    @Override
    protected String getBaseUrl() {
        return RestTestFixtures.COMPANY_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.COMPANY_ID_URL;
    }
}
