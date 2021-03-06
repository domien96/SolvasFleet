package solvas.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import solvas.Application;
import solvas.authentication.WebSecurityConfig;
import solvas.authentication.jwt.JwtSettings;
import solvas.persistence.api.dao.TestConfig;
import solvas.persistence.hibernate.HibernateConfig;
import solvas.rest.api.models.ApiModel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        Application.class,
        HibernateConfig.class,
        TestConfig.class,
        WebSecurityConfig.class,
        MethodSecurityConfig.class,
        JwtSettings.class
})
@ComponentScan//("solvas")
@EnableWebSecurity
@ActiveProfiles("debug")
@WebAppConfiguration
/**
 * Abstract class to test authorization on rest controllers
 */
public abstract class AbstractAuthorizationTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    protected static String adminToken;
    protected static String nopermissionToken;

    private static boolean setUpIsDone = false;

    @Before
    public void setUp() throws Exception {
        if (setUpIsDone)
            return;

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        String response = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(UserFixtures.ADMINISTRATOR))).andReturn().getResponse().getContentAsString();
        adminToken = JsonPath.read(response, "accessToken.token");
        response = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(UserFixtures.NO_PERMISSIONS))).andReturn().getResponse().getContentAsString();
        nopermissionToken = JsonPath.read(response,"accessToken.token");
        setUpIsDone = true;
    }

    /**
     * Configure MockMVC to use Spring Security
     * @return MockMVC
     */
    public MockMvc getMockMvc()
    {
        return MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(MockMvcResultHandlers.print())
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    /**
     * Get URL to POST/GET to
     * @return The url
     */
    public abstract String getUrl();

    /**
     * Get URL to PUT/DESTROY to
     * @return The url
     */
    public abstract String getIdUrl();

    /**
     * Authenticate a request
     * @param builder Request builder
     * @param token Access token
     * @return Builder that will generate an authenticated request
     */
    protected MockHttpServletRequestBuilder auth(MockHttpServletRequestBuilder builder, String token) {
        return builder.header("X-Authorization","Bearer "+token);
    }

    /**
     * Expect admin user to be able to read this model
     * @throws Exception When test fails
     */
    @Test
    public void userCanReadModel() throws Exception {
        getMockMvc().perform(auth(get(getIdUrl()),adminToken))
                .andExpect(status().isOk());
    }

    /**
     * Expect user without permissions to be unable to read this model
     * @throws Exception When test fails
     */
    @Test
    public void userCantReadModel() throws Exception {
        getMockMvc().perform(auth(get(getIdUrl()),nopermissionToken))
                .andExpect(status().isForbidden());
    }

    /**
     * Expect admin user to be able to read all models
     * @throws Exception When test fails
     */
    @Test
    public void userCanReadModels() throws Exception {
        getMockMvc().perform(auth(get(getUrl()),adminToken))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    /**
     * Expect user without permissions to be unable to read any model
     * @throws Exception When test fails
     */
    @Test
    public void userCantReadModels() throws Exception {
        getMockMvc().perform(auth(get(getUrl()),nopermissionToken)).andExpect(status().isForbidden());
    }

    /**
     * Expect admin user to be able to create new instance
     * @throws Exception When test fails
     */
    @Test
    public void userCanPostModel() throws Exception {
        getMockMvc().perform(auth(post(getUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getModelJson()),adminToken)).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }

    /**
     * Expect user without permissions to be unable to create any instance
     * @throws Exception When test fails
     */
    @Test
    public void userCantPostModel() throws Exception {
        getMockMvc().perform(auth(post(getUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getModelJson()),nopermissionToken)).andExpect(status().isForbidden()).andDo(MockMvcResultHandlers.print());
    }

    /**
     * Expect admin user to be able to alter any instance
     * @throws Exception When test fails
     */
    @Test
    public void userCanPutModel() throws Exception {
        getMockMvc().perform(auth(put(getIdUrl()), adminToken).contentType(MediaType.APPLICATION_JSON_UTF8).content(getModelJson())).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    /**
     * Expect user without permissions to be unable to alter any instance
     * @throws Exception When test fails
     */
    @Test
    public void userCantPutModel() throws Exception {
        getMockMvc().perform(auth(put(getIdUrl()), nopermissionToken).contentType(MediaType.APPLICATION_JSON_UTF8).content(getModelJson())).andExpect(status().isForbidden());
    }

    /**
     * Expect admin user to be able to archive any instance
     * @throws Exception When test fails
     */
    @Test
    public void userCanDeleteModel() throws Exception {
        getMockMvc().perform(auth(delete(getIdUrl()),adminToken)).andExpect(status().isNoContent());
    }

    /**
     * Expect user without permissions to be unable to archive any instance
     * @throws Exception When test fails
     */
    @Test
    public void userCantDeleteModel() throws Exception {
        getMockMvc().perform(auth(delete(getIdUrl()),nopermissionToken)).andExpect(status().isForbidden());
    }

    public String getModelJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(getModel());
    }

    /**
     * @return An instance of ApiModel
     */
    public abstract ApiModel getModel();
}
