package solvas.rest;

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
import solvas.service.models.Company;

/**
 * Integration tests of the CompanyRestController
 * It checks HTTP responses
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
