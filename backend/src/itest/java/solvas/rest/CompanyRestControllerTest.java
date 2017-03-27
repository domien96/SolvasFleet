package solvas.rest;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Company;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.CompanyRestController;
import solvas.rest.service.AbstractService;
import solvas.rest.service.CompanyService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Integration tests of the CompanyRestController
 * It checks HTTP responses and calls to the CompanyDao
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyRestControllerTest extends AbstractRestControllerTest<Company,ApiCompany>{


    @Mock
    private CompanyService service;


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
        return new CompanyRestController(service);
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
        return RestTestFixtures.COMPANYROOTURL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.COMPANYIDURL;
    }
}
