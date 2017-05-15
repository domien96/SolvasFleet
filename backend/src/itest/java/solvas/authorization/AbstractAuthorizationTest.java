package solvas.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import solvas.Application;
import solvas.authentication.WebSecurityConfig;
import solvas.authentication.ajax.LoginRequest;
import solvas.authentication.jwt.JwtSettings;
import solvas.authorization.MethodSecurityConfig;
import solvas.persistence.api.dao.TestConfig;
import solvas.persistence.hibernate.HibernateConfig;
import solvas.rest.api.models.ApiModel;
import solvas.service.models.Model;

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
public abstract class AbstractAuthorizationTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private FilterChainProxy filterChainProxy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    public MockMvc getMockMvc()
    {
        return mockMvc;
    }

    public abstract String getUrl();

    @Test
    public void testAnonymousUserHasNoPermissions() throws Exception {
        testAllDenied(null);
    }

    @Test
    public void testUserWithoutPermission() throws Exception {
        testAllDenied(UserFixtures.NO_PERMISSIONS);
    }

    @Test
    public void testUserAccessForAccount() throws Exception {
        mockMvc.perform(authenticate(get("/users"), UserFixtures.ADMINISTRATOR))
                .andExpect(status().isOk());
    }

    private MockHttpServletRequestBuilder authenticate(MockHttpServletRequestBuilder builder, LoginRequest user) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String response = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(user))).andReturn().getResponse().getContentAsString();
        String token = JsonPath.read(response, "accessToken.token");
        return builder.header("X-Authorization", "Bearer " + token);
    }

    private void testAllDenied(LoginRequest user) throws Exception {
        testDenied(get("/users"), user);

        testDenied(post("/companies").contentType(MediaType.APPLICATION_JSON_UTF8).content("{}"), user);
        testDenied(put("/companies/1").contentType(MediaType.APPLICATION_JSON_UTF8).content("{}"), user);
        testDenied(delete("/companies/1"), user);
        testDenied(get("/companies/1"), user);
        testDenied(get("/companies/1/contracts"), user);

        /*testDenied(get("/contracts/1"), user);
        testDenied(put("/contracts/1").contentType(MediaType.APPLICATION_JSON_UTF8).content("{}"), user);
        testDenied(delete("/contracts/1"), user);
        testDenied(post("/contracts").contentType(MediaType.APPLICATION_JSON_UTF8).content("{}"), user);
*/
    }

    private void testDenied(MockHttpServletRequestBuilder b, LoginRequest user) throws Exception {
        ResultMatcher r = status().isUnauthorized();
        if (user != null) {
            b = authenticate(b, user);
            r = status().isForbidden();
        }
        System.out.println(mockMvc.perform(b).andReturn().getResponse().getContentAsString());
        mockMvc.perform(b)
                .andExpect(r);

    }


    @Test
    public void userCanReadModel() throws Exception {
        getMockMvc().perform(authenticate(get(getUrl()),UserFixtures.ADMINISTRATOR)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void userCanPostModel() throws Exception {
        getMockMvc().perform(authenticate(post(getUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(new ObjectMapper().writeValueAsString(getModel())),UserFixtures.ADMINISTRATOR)).andExpect(status().isOk());
    }

    @Test
    public void userCanPutModel() throws Exception {
        getMockMvc().perform(authenticate(put(getUrl()+'1'),UserFixtures.ADMINISTRATOR)).andExpect(status().isOk());
    }

    @Test
    public void userCantReadModel() throws Exception {
        testDenied(get(getUrl()),UserFixtures.NO_PERMISSIONS);
    }

    public abstract ApiModel getModel();
}
