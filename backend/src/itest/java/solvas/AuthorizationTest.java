package solvas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import solvas.authentication.WebSecurityConfig;
import solvas.authorization.MethodSecurityConfig;
import solvas.persistence.api.dao.TestConfig;
import solvas.persistence.hibernate.HibernateConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Application.class, HibernateConfig.class, TestConfig.class, WebSecurityConfig.class, MethodSecurityConfig.class})
@ComponentScan("solvas")
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
        mockMvc.perform(get("/api/user/account")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void testUserAccessForAccount() throws Exception{
        mockMvc.perform(get("/auth/login")).andExpect(status().isOk());
    }
}
