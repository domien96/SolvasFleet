package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import matchers.RoleMatcher;
import matchers.TestMatcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Role;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.role.RoleDao;
import solvas.rest.controller.RoleRestController;

import java.util.Collections;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests of the RoleRestController
 * It checks HTTP responses and calls to the RoleDao
 */
public class RoleRestControllerTest {
    @InjectMocks
    private RoleRestController roleRestController;

    @Mock
    private RoleDao roleDaoMock;
    private MockMvc mockMvc;

    private ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
    private TestMatcher<Role> matcher=new RoleMatcher();

    private Role role;
    private String json;

    /**
     * Setup of mockMVC
     * currently provides one random role object and its json representation
     */
    @Before
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(roleRestController).build();
        role=random(Role.class);
        ObjectMapper mapper=new ObjectMapper();
        mapper.findAndRegisterModules(); //Include module to convert LocaleDateTime objects
        json=mapper.writeValueAsString(role);
    }

    /**
     * Test: the response of a get request for a user that exists on the db
     */
    @Test
    public void getRoleByIdNoError() throws Exception {
        when(roleDaoMock.find(anyInt())).thenReturn(role);
        ResultActions resultActions=
                mockMvc.perform(get("/roles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matcher.jsonMatcher(resultActions,role);
    }

    /**
     * Test: the response of a get request for a role that doesn't exist on the db
     */
    @Test
    public void getRoleByIdNotFound() throws Exception {
        when(roleDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a get request for all the roles
     */
    @Test
    public void getRolesNoError() throws Exception {
        when(roleDaoMock.findAll()).thenReturn(Collections.singletonList(role));
        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        //todo json expectation
    }

    /**
     * Test: the response of a put request for a role that exists on the db
     */
    @Test
    public void putRoleNoError() throws Exception {
        when(roleDaoMock.save(any())).thenReturn(role);
        ResultActions resultActions=
                mockMvc.perform(put("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());

        matcher.jsonMatcher(resultActions,role);


        // Verificatie actie naar dao
        verify(roleDaoMock,times(1)).save(captor.capture());
        matcher.performAsserts(role,captor.getValue());
    }

    /**
     * Test: the response of a put request for role that doesn't exists on the db
     */
    @Test
    public void putRoleNotFound() throws Exception {
        when(roleDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a post request for a new role that doesn't exist on the db
     */
    @Test
    public void postRoleNoError() throws Exception {
        when(roleDaoMock.save(any())).thenReturn(role);

        when(roleDaoMock.find(anyInt())).thenReturn(role);
        ResultActions resultActions=
                mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());
        matcher.jsonMatcher(resultActions,role);

        verify(roleDaoMock,times(1)).save(captor.capture());
        matcher.performAsserts(role,captor.getValue());
    }


    /**
     * Test: the response of a post request for a role that exists on the db (error)
     */
    @Ignore
    @Test //todo
    public void postRoleAlreadyExists() throws Exception {
    }

    /**
     * Test: the response of a destroy request for a user that exists
     */
    @Ignore //on hold
    @Test
    public void destroyUser_noError() throws Exception {
        when(roleDaoMock.destroy(any())).thenReturn(role);
        mockMvc.perform(delete("/v").contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
    }

    /**
     * Test: the response of a destroy request for a user that doesn't exist
     */
    @Ignore //on hold
    @Test
    public void destroyUser_notFound() throws Exception {
        when(roleDaoMock.destroy(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/roles").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }
}
