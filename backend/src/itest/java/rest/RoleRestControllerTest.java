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
import java.util.Random;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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


    //testing with 1 object for the time being
    private Role createDefaultRole()
    {
        Role r= new Role(null,"func",null,null,null,"test.be");
        r.setId(new Random().nextInt(100));
        return r;
    }

    @Before
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(roleRestController).build();
        role=createDefaultRole();
        json=new ObjectMapper().writeValueAsString(role);
    }

    @Test
    public void getRoleById_noError() throws Exception {
        when(roleDaoMock.find(anyInt())).thenReturn(role);
        ResultActions resultActions=
                mockMvc.perform(get("/roles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        matcher.jsonMatcher(resultActions,role);
    }

    @Test
    public void getRoleById_notFound() throws Exception {
        when(roleDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRoles_noError() throws Exception {
        when(roleDaoMock.findAll()).thenReturn(Collections.singletonList(role));
        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        //todo json expectation
    }

    @Test
    public void putRole_noError() throws Exception {
        when(roleDaoMock.save(any())).thenReturn(role);
        ResultActions resultActions=
                mockMvc.perform(put("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());

        matcher.jsonMatcher(resultActions,role);


        // Verificatie actie naar dao
        verify(roleDaoMock,times(1)).save(captor.capture());
        matcher.performAsserts(role,captor.getValue());
    }

    @Test
    public void putRole_notFound() throws Exception {
        when(roleDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        Role update = createDefaultRole();
        String json=new ObjectMapper().writeValueAsString(update);

        mockMvc.perform(put("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void postRole_noError() throws Exception {
        Role role = createDefaultRole();
        String json = new ObjectMapper().writeValueAsString(role);
        when(roleDaoMock.save(any())).thenReturn(role);

        ResultActions resultActions=
                mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());
        matcher.jsonMatcher(resultActions,role);

        verify(roleDaoMock,times(1)).save(captor.capture());
        matcher.performAsserts(role,captor.getValue());
    }


    @Ignore
    @Test //todo
    public void postRole_alreadyExists() throws Exception {

    }


}
