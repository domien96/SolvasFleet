package solvas.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import solvas.models.Company;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.CompanyDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.CompanyMapper;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.controller.CompanyRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

/**
 * Test the controller return values.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CompanyRestController.class)
public class CompanyRestControllerTest extends AbstractControllerTest<Company, ApiCompany> {

    @MockBean
    private CompanyMapper companyMapper;

    @MockBean
    private CompanyDao companyDaoMock;

    private ApiCompany validCompany;

    /**
     * Setup of mockMVC
     * currently provides one random company object and its json representation
     */
    @Before
    public void setUp() throws JsonProcessingException {
        validCompany = random(ApiCompany.class);
        // Set valid phone number
        validCompany.setPhoneNumber("+32 56 22 56 99");
        validCompany.setId(500);
    }

    @Override
    protected Dao<Company> getMockDao() {
        return companyDaoMock;
    }

    @Override
    protected AbstractMapper<Company, ApiCompany> getMockMapper() {
        return companyMapper;
    }

    @Override
    protected ApiCompany getValidModel() {
        return validCompany;
    }

    @Override
    protected String getBaseUrl() {
        return "/companies/";
    }

    @Override
    protected Class<ApiCompany> getApiClass() {
        return ApiCompany.class;
    }

    @Override
    protected Class<Company> getModelClass() {
        return Company.class;
    }
}