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


public class CompanyRestControllerTest {
    @InjectMocks
    private CompanyRestController companyRestController;

    @Mock
    private CompanyDao companyDaoMock;

    private MockMvc mockMvc;

    private ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);

    private CompanyMatcher matcher = new CompanyMatcher();

    private Company company;

    private String json;

    @Before
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(companyRestController).build();
        company=random(Company.class);
        json=new ObjectMapper().writeValueAsString(company);
    }
    @Test
    public void getCompanyByIdNoError() throws Exception {
        when(companyDaoMock.find(anyInt())).thenReturn(company);
        ResultActions res =mockMvc.perform(get("/companies/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matcher.jsonMatcher(res,company);
    }

    @Test
    public void getCompanyByIdNotFound() throws Exception {
        when(companyDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCompaniesNoError() throws Exception {
        when(companyDaoMock.findAll()).thenReturn(Collections.singletonList(company));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

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

    @Test
    public void postCompanyAlreadyExists()
    {
        //todo, ?http response?
    }

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

    @Test
    public void putCompanyNotFound() throws Exception {
        when(companyDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }
}
