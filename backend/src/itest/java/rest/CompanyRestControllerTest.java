package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Company;
import solvas.models.validators.CompanyValidator;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;
import solvas.rest.api.mappers.CompanyMapper;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.controller.CompanyRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the CompanyRestController
 * It checks HTTP responses and calls to the CompanyDao
 */
public class CompanyRestControllerTest{
    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private DaoContext context;

    @Mock
    private CompanyValidator companyValidator;

    @Mock
    private CompanyDao companyDaoMock;

    private MockMvc mockMvc;

    private ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);

    private ApiCompany apiCompany;
    private String json;


    /**
     * Setup of mockMVC
     * currently provides one random company object and its json representation
     */
    @Before
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(context.getCompanyDao()).thenReturn(companyDaoMock);
        CompanyRestController companyRestController=new CompanyRestController(context,companyMapper,companyValidator);
        mockMvc= MockMvcBuilders.standaloneSetup(companyRestController).build();

        apiCompany=random(ApiCompany.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        json=mapper.writeValueAsString(apiCompany);
        }

    /**
     * Test: the response of a get request for a company that exists on the db
     */
    @Test
    public void getCompanyByIdNoError() throws Exception {
        when(companyMapper.convertToApiModel(any())).thenReturn(apiCompany);
        ResultActions res =mockMvc.perform(get("/companies/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());
        matchCompanyJson(res,apiCompany);
    }

    /**
     * Test: the response of a get request for a company that doesn't exist on the db
     */
    @Test
    public void getCompanyByIdNotFound() throws Exception {
        when(companyDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a get request for all the companies
     */
    @Test
    public void getCompaniesNoError() throws Exception {
        when(companyMapper.convertToApiModel(any())).thenReturn(random(ApiCompany.class));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Test: the response of a post request for a new company that doesn't exist on the db
     */
    @Test
    public void postCompanyNoError() throws Exception {
        when(companyMapper.convertToApiModel(any())).thenReturn(apiCompany);
        doNothing().when(companyValidator).validate(any(),any());
        ResultActions resultActions=
                mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                        .andExpect(status().isOk());
        matchCompanyJson(resultActions,apiCompany);
        verify(companyDaoMock,times(1)).create(captor.capture());
    }

    /**
     * Test: the response of a post request for a role that exists on the db (error)
     */
    @Ignore
    @Test
    public void postCompanyAlreadyExists()
    {
        //todo, ?http response?
    }

    /**
     * Test: the response of a put request for a role that exists on the db
     */
    @Test
    public void putCompanyNoError() throws Exception {
        when(companyMapper.convertToApiModel(any())).thenReturn(apiCompany);
        when(companyMapper.convertToModel(any())).thenReturn(random(Company.class));
        ResultActions resultActions =
                mockMvc.perform(put("/companies/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matchCompanyJson(resultActions,apiCompany);
        verify(companyDaoMock,times(1)).update(captor.capture());
     }

    /**
     * Test: the response of a post request for a role that doesn't exist on the db (error)
     */
    @Test
    public void putCompanyNotFound() throws Exception {
        when(companyDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        when(companyMapper.convertToModel(any())).thenReturn(random(Company.class));
        mockMvc.perform(put("/companies/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a destroy requests for a company that exists
     */
    @Test
    public void destroyCompanyNoError() throws Exception {

        when(companyDaoMock.destroy(any())).thenReturn(random(Company.class));
        mockMvc.perform(delete("/companies/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());
    }

    /**
     * Test: the response of a destroy requests for a company that doesn't exists
     */
    @Test
    public void destroyCompanyNotFound() throws Exception {
        when(companyDaoMock.destroy(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/companies/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

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

}
