package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import matchers.CompanyMatcher;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Company;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.company.CompanyDao;
import solvas.rest.controller.CompanyRestController;

import java.util.Collections;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests of the CompanyRestController
 * It checks HTTP responses and calls to the CompanyDao
 */
public class CompanyRestControllerTest{
    @InjectMocks
    private CompanyRestController companyRestController;

    @Mock
    private CompanyDao companyDaoMock;
    private MockMvc mockMvc;

    private ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);
    private CompanyMatcher matcher = new CompanyMatcher();

    private Company company;
    private String json;

    /**
     * Setup of mockMVC
     * currently provides one random company object and its json representation
     */
    @Before
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(companyRestController).build();
        company=random(Company.class);
        json=new ObjectMapper().writeValueAsString(company);
    }

    /**
     * Test: the response of a get request for a company that exists on the db
     */
    @Test
    public void getCompanyByIdNoError() throws Exception {
        when(companyDaoMock.find(anyInt())).thenReturn(company);
        ResultActions res =mockMvc.perform(get("/companies/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matcher.jsonMatcher(res,company);
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
        when(companyDaoMock.findAll()).thenReturn(Collections.singletonList(company));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * Test: the response of a post request for a new company that doesn't exist on the db
     */
    @Test
    public void postCompanyNoError() throws Exception {
        when(companyDaoMock.save(any())).thenReturn(company);
        ResultActions resultActions=
                mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                        .andExpect(status().isOk());

        matcher.jsonMatcher(resultActions,company);

        //check content sent to dao
        verify(companyDaoMock,times(1)).save(captor.capture());

        matcher.performAsserts(captor.getValue(),company);

    }

    /**
     * Test: the response of a post request for a role that exists on the db (error)
     */
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
        when(companyDaoMock.save(any())).thenReturn(company);
        ResultActions resultActions =
                mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matcher.jsonMatcher(resultActions,company);
        verify(companyDaoMock,times(1)).save(captor.capture());
        matcher.performAsserts(company,captor.getValue());
    }

    /**
     * Test: the response of a post request for a role that doesn't exist on the db (error)
     */
    @Test
    public void putCompanyNotFound() throws Exception {
        when(companyDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }
}
