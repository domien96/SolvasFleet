package solvas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import solvas.authentication.WebSecurityConfig;
import solvas.authentication.jwt.JwtSettings;
import solvas.authorization.MethodSecurityConfig;
import solvas.persistence.api.dao.TestConfig;
import solvas.persistence.hibernate.HibernateConfig;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
@WebAppConfiguration
public class AuthorizationTest {

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

    @Test
    public void testAnonymous() throws Exception {
        mockMvc.perform(get("/api/user/account")).andExpect(status().isUnauthorized());
    }

    @Test
    public void testUserAccessForAccount() throws Exception {
        String token = getToken("metus.vitae.velit@facilisismagnatellus.net", "WBL90DHP2RU");
        mockMvc.perform(get("/companies").header("X-Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    private String getToken(String username, String password) throws Exception {
        ObjectMapper mapper=new ObjectMapper();
        mapper.findAndRegisterModules();
        ObjectNode o = mapper.createObjectNode();
        o.put("username", username);
        o.put("password", password);
        String response = mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON_UTF8).content(o.toString())).andReturn().getResponse().getContentAsString();
        System.out.println(response);
        return JsonPath.read(response, "accessToken.token");
    }
}
