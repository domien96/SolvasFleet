package solvas.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import solvas.models.Company;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;
import solvas.rest.api.mappers.CompanyMapper;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.controller.CompanyRestController;
import solvas.rest.utils.JsonListWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomCollectionOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test the controller return values.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CompanyRestController.class)
public class CompanyRestControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CompanyMapper companyMapper;

    @MockBean
    private CompanyDao companyDaoMock;

    private ApiCompany validCompany;

    private String validCompanyJson;

    private ArgumentCaptor<Company> captor = ArgumentCaptor.forClass(Company.class);

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
        validCompanyJson = mapper.writeValueAsString(validCompany);
    }

    /**
     * Test: the response of a get request for a company that exists on the db
     */
    @Test
    public void getCompanyByIdNoError() throws Exception {
        when(companyMapper.convertToApiModel(any(Company.class))).thenReturn(validCompany);
        mockMvc.perform(get("/companies/" + validCompany.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(validCompanyJson))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test: the response of a get request for a company that doesn't exist on the db
     */
    @Test
    public void getCompanyByIdNotFound() throws Exception {
        when(companyDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/companies/" + validCompany.getId() + 1))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a get request for all the companies.
     */
    @Test
    public void getNoCompaniesNoError() throws Exception {

        // Empty page
        Page<Company> emptyPage = new PageBuilder<Company>()
                .totalElements(0)
                .build();

        String result = mapper.writeValueAsString(JsonListWrapper.forPageable(emptyPage));

        when(companyDaoMock.findAll(any(), any(Pageable.class))).thenReturn(emptyPage);
        when(companyMapper.convertToApiModel(any())).thenReturn(random(ApiCompany.class));

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(result));
    }

    /**
     * Test: the response of a get request for all the companies.
     */
    @Test
    public void get10CompaniesNoError() throws Exception {

        List<Company> companies = new ArrayList<>(randomCollectionOf(10, Company.class));

        // Empty page
        Page<Company> page = new PageBuilder<Company>()
                .elements(companies)
                .totalElements(companies.size())
                .build();

        Map<Company, ApiCompany> conversion = new HashMap<>();
        companies.forEach(c -> conversion.put(c, random(ApiCompany.class)));

        Page<ApiCompany> converted = page.map(conversion::get);

        String result = mapper.writeValueAsString(JsonListWrapper.forPageable(converted));

        when(companyDaoMock.findAll(any(), any(Pageable.class))).thenReturn(page);
        when(companyMapper.convertToApiModel(any())).then(invocation -> {
            return conversion.get((Company) invocation.getArguments()[0]);
        });

        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(result));
    }

    /**
     * Test: the response of a post request for a new company that doesn't exist on the db
     */
    @Test
    public void postCompanyNoError() throws Exception {
        when(companyMapper.convertToApiModel(any())).thenReturn(validCompany);

        mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(validCompanyJson))
                .andExpect(status().isOk())
                .andExpect(content().json(validCompanyJson));

        verify(companyDaoMock, times(1)).save(captor.capture());
    }

    /**
     * Test: the response of a put request for a role that exists on the db
     */
    @Test
    public void putCompanyNoError() throws Exception {

        // Return anything
        when(companyDaoMock.find(anyInt())).thenReturn(random(Company.class));
        // Return the correct company.
        when(companyMapper.convertToApiModel(any())).thenReturn(validCompany);
        when(companyMapper.convertToModel(any())).thenReturn(random(Company.class));

        mockMvc.perform(put("/companies/" + validCompany.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(validCompanyJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(validCompanyJson));

        verify(companyDaoMock,times(1)).update(captor.capture());
     }

    /**
     * Test: the response of a post request for a role that doesn't exist on the db (error)
     */
    @Test
    public void putCompanyNotFound() throws Exception {
        when(companyDaoMock.update(any())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put("/companies/" + validCompany.getId() + 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(validCompanyJson))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a destroy requests for a company that exists
     */
    @Test
    public void destroyCompanyNoError() throws Exception {
        when(companyDaoMock.destroy(any())).thenReturn(random(Company.class));
        mockMvc.perform(delete("/companies/" + validCompany.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
        verify(companyDaoMock,times(1)).destroy(anyInt());
    }

    /**
     * Test: the response of a destroy requests for a company that doesn't exists
     */
    @Test
    public void destroyCompanyNotFound() throws Exception {
        when(companyDaoMock.destroy(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/companies/" + validCompany.getId() + 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }
}