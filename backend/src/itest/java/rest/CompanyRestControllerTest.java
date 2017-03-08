package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Company;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.company.CompanyDao;
import solvas.rest.controller.CompanyRestController;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CompanyRestControllerTest {
    @InjectMocks
    private CompanyRestController companyRestController;

    @Mock
    private CompanyDao companyDaoMock;

    private MockMvc mockMvc;

    private Company company;


    @Before
    public void setUp() throws ParseException, IOException {
        company = new Company("Fortis", "vatnraaa","0483948213","fortislaan","fortis.be");

        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(companyRestController).build();
    }
    @Test
    public void getCompanyById_noerror() throws Exception {
        company.setId(10);
        when(companyDaoMock.find(anyInt())).thenReturn(company);
        mockMvc.perform(get("/companies/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id").value(10));
    }

    @Test
    public void getCompanyById_notFound() throws Exception {
        when(companyDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCompanies_noError() throws Exception {
        when(companyDaoMock.findAll()).thenReturn(Collections.singletonList(company));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void postCompany_noError() throws Exception {
        when(companyDaoMock.find(anyInt())).thenReturn(null);
        String json=new ObjectMapper().writeValueAsString(company);
        company.setId(30);
        when(companyDaoMock.save(any())).thenReturn(company);
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(30));
        //todo check content
        verify(companyDaoMock,times(1)).save(any());

    }

    @Test
    public void postCompany_alreadyExists()
    {
        //todo, ?http response?
    }

    @Test
    public void putCompany_noError() throws Exception {
        company.setId(30);
        Company update = new Company("Fortis", "new","0032","updateLane","fortis.com");
        update.setId(30);
        String json = new ObjectMapper().writeValueAsString(update);
        when(companyDaoMock.find(anyInt())).thenReturn(company);
        when(companyDaoMock.save(any())).thenReturn(update);
        mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("vatNumber").value("new"))
                .andExpect(jsonPath("phoneNumber").value("0032"))
                .andExpect(jsonPath("address").value("updateLane"))
                .andExpect(jsonPath("url").value("fortis.com"));
        verify(companyDaoMock,times(1)).save(any());
    }

    @Test
    public void putCompany_notFound() throws Exception {
        when(companyDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        String json = new ObjectMapper().writeValueAsString(company);
        mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }









}
