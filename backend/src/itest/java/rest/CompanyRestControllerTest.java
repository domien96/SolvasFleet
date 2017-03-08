package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Company;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.company.CompanyDao;
import solvas.rest.controller.CompanyRestController;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CompanyRestControllerTest {
    @InjectMocks
    private CompanyRestController companyRestController;

    @Mock
    private CompanyDao companyDaoMock;

    private MockMvc mockMvc;

    private ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);

    private Company createDefaultCompany()
    {
        Company c= new Company("exclusi","vakjzkjl","04882828","godLane","rand.be");
        c.setId(new Random().nextInt(100));
        return c;
    }

    @Before
    public void setUp() throws ParseException, IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(companyRestController).build();
    }
    @Test
    public void getCompanyById_noerror() throws Exception {
        Company company = createDefaultCompany();
        when(companyDaoMock.find(anyInt())).thenReturn(company);
        mockMvc.perform(get("/companies/10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id").value(company.getId()))
                .andExpect(jsonPath("name").value(company.getName()))
                .andExpect(jsonPath("address").value(company.getAddress()));
    }

    @Test
    public void getCompanyById_notFound() throws Exception {
        when(companyDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCompanies_noError() throws Exception {
        when(companyDaoMock.findAll()).thenReturn(Collections.singletonList(createDefaultCompany()));
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void postCompany_noError() throws Exception {
        when(companyDaoMock.find(anyInt())).thenReturn(null);
        Company company=createDefaultCompany();
        String json=new ObjectMapper().writeValueAsString(company);
        when(companyDaoMock.save(any())).thenReturn(company);
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(company.getId()));

        //check content sent to dao
        verify(companyDaoMock,times(1)).save(captor.capture());
        assertEquals("id correctly sent to dao",company.getId(),captor.getValue().getId());

    }

    @Test
    public void postCompany_alreadyExists()
    {
        //todo, ?http response?
    }

    @Test
    public void putCompany_noError() throws Exception {
        Company update = createDefaultCompany();

        String json = new ObjectMapper().writeValueAsString(update);
        when(companyDaoMock.save(any())).thenReturn(update);
        mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(update.getId()))
                .andExpect(jsonPath("address").value(update.getAddress()))
                .andExpect(jsonPath("name").value(update.getName()));

        verify(companyDaoMock,times(1)).save(captor.capture());

        assertEquals("id correctly sent to dao",update.getId(),captor.getValue().getId());
        assertEquals("address correctly sent to dao",update.getAddress(),captor.getValue().getAddress());
        assertEquals("name correctly sent to dao",update.getName(),captor.getValue().getName());
    }

    @Test
    public void putCompany_notFound() throws Exception {
        when(companyDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        String json = new ObjectMapper().writeValueAsString(createDefaultCompany());
        mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }
}
