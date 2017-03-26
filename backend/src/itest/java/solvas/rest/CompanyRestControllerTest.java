package solvas.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import solvas.models.Company;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.CompanyRestController;
import solvas.rest.service.AbstractService;
import solvas.rest.service.CompanyService;
import solvas.rest.utils.JsonListWrapper;

import static io.github.benas.randombeans.api.EnhancedRandom.randomSetOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the CompanyRestController
 * It checks HTTP responses and calls to the CompanyDao
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyRestControllerTest extends AbstractRestControllerTest<Company,ApiCompany>{


    @Mock
    private CompanyService service;


    public CompanyRestControllerTest() {
        super(ApiCompany.class);
    }


    /**
     * Method to check if json has the correct attributes
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
        return "/companies";
    }

    @Override
    public String getIdUrl() {
        return "/companies/10";
    }
}
