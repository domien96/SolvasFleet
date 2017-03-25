package rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.CompanyRestController;
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
public class CompanyRestControllerTest extends AbstractRestControllerTest<ApiCompany>{


    @Mock
    private CompanyService service;


    public CompanyRestControllerTest() {
        super(ApiCompany.class);
    }


    /**
     * Test: the response of a get request for a company that exists on the db
     */
    @Test
    public void getCompanyByIdNoError() throws Exception {
        when(service.getById(anyInt())).thenReturn(getTestModel());
        ResultActions res =getMockMvc().perform(get(TestFixtures.companyIdUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());
        matchCompanyJson(res,getTestModel());
    }

    /**
     * Test: the response of a get request for a company that doesn't exist on the db
     */
    @Test
    public void getCompanyByIdNotFound() throws Exception {
        when(service.getById(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc().perform(get(TestFixtures.companyIdUrl))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a get request for all the companies
     */
    @Test
    public void getCompaniesNoError() throws Exception {
        when(service.findAndWrap(any(),any())).thenReturn(new JsonListWrapper<>(randomSetOf(10,ApiCompany.class)));
        getMockMvc().perform(get(TestFixtures.companyBaseUrl))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Test: the response of a post request for a new company that doesn't exist on the db
     */
    @Test
    public void postCompanyNoError() throws Exception {
        when(service.create(any())).thenReturn(getTestModel());
        ResultActions resultActions=
                getMockMvc().perform(post(TestFixtures.companyBaseUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                        .andExpect(status().isOk());
        matchCompanyJson(resultActions,getTestModel());
    }

    /**
     * Test: the response of a put request for a role that exists on the db
     */
    @Test
    public void putCompanyNoError() throws Exception {
        when(service.update(anyInt(),any())).thenReturn(getTestModel());
        ResultActions resultActions =
                getMockMvc().perform(put(TestFixtures.companyIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matchCompanyJson(resultActions,getTestModel());
     }

    /**
     * Test: the response of a post request for a role that doesn't exist on the db (error)
     */
    @Test
    public void putCompanyNotFound() throws Exception {
        when(service.update(anyInt(),any())).thenThrow(new EntityNotFoundException());
        getMockMvc().perform(put(TestFixtures.companyIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a destroy requests for a company that exists
     */
    @Test
    public void destroyCompanyNoError() throws Exception {
        getMockMvc().perform(delete(TestFixtures.companyIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isOk());
    }

    /**
     * Test: the response of a destroy requests for a company that doesn't exists
     */
    @Test
    public void destroyCompanyNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).destroy(anyInt());
        getMockMvc().perform(delete(TestFixtures.companyIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNotFound());
    }


    /**
     * Method to check if json has the correct attributes
     */
    private void matchCompanyJson(ResultActions resultActions, ApiCompany source) throws Exception {
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
}
