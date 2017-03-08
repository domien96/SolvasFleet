package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CompanyRestControllerTest extends AbstractRestTest{
    @InjectMocks
    private CompanyRestController companyRestController;

    @Mock
    private CompanyDao companyDaoMock;

    private MockMvc mockMvc;

    private TestObjectProvider testProvider=new TestObjectProvider();

    private Company company;
    private List<Company> companies;


    @Before
    public void setUp() throws ParseException, IOException {
        company=testProvider.getCompany();
        companies=testProvider.getCompanies();
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(companyRestController).build();
    }
    @Test
    public void getCompanyById_noerror() throws Exception {
        when(companyDaoMock.find(anyInt())).thenReturn(company);
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(json(company)));
    }

    @Test
    public void getCompanyById_notFound() throws Exception {
        when(companyDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/companies/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getCompanies_noError() throws Exception {
        when(companyDaoMock.findAll()).thenReturn(companies);
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
             //   .andExpect(content().string(json(companies)));
    }

    @Test
    public void postCompany_noError() throws Exception {
        when(companyDaoMock.find(anyInt())).thenReturn(null);
        mockMvc.perform(post("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json(company)))
                .andExpect(status().isOk());
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
        when(companyDaoMock.find(anyInt())).thenReturn(company);
        when(companyDaoMock.save(any())).thenReturn(company);
        mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json(company)))
                .andExpect(status().isOk());
        verify(companyDaoMock,times(1)).save(any());
    }

    @Test
    public void putCompany_notFound() throws Exception {
        when(companyDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content(json(company)))
                .andExpect(status().isNotFound());
    }









}
