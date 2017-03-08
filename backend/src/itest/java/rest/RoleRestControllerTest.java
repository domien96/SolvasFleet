package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Role;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.role.RoleDao;
import solvas.rest.controller.RoleRestController;

import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RoleRestControllerTest {
    @InjectMocks
    private RoleRestController roleRestController;

    @Mock
    private RoleDao roleDaoMock;

    private MockMvc mockMvc;

    private ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);


    private Role createDefaultRole()
    {
        Role r= new Role(null,"func",null,null,null,"test.be");
        r.setId(new Random().nextInt(100));
        return r;
    }

    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(roleRestController).build();
    }

    @Test
    public void getRoleById_noError() throws Exception {
        Role role = createDefaultRole();
        when(roleDaoMock.find(anyInt())).thenReturn(role);
        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id").value(role.getId()))
                .andExpect(jsonPath("function").value(role.getFunction()));
    }

    @Test
    public void getRoleById_notFound() throws Exception {
        when(roleDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRoles_noError() throws Exception {
        when(roleDaoMock.findAll()).thenReturn(Collections.singletonList(createDefaultRole()));
        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        //todo json expectation
    }

    @Test
    public void putRole_noError() throws Exception {
        Role update = createDefaultRole();
         String json=new ObjectMapper().writeValueAsString(update);
         when(roleDaoMock.save(any())).thenReturn(update);
        mockMvc.perform(put("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("function").value(update.getFunction()))
                .andExpect(jsonPath("id").value(update.getId()));

        // Verificatie actie naar dao
        verify(roleDaoMock,times(1)).save(captor.capture());
        assertEquals("id passed to dao",update.getId(),captor.getValue().getId());
        assertEquals("function passed to dao",update.getFunction(),captor.getValue().getFunction());
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

        mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(role.getId()))
                .andExpect(jsonPath("function").value(role.getFunction()));
        verify(roleDaoMock,times(1)).save(captor.capture());
        assertEquals("id passed correctly to dao",role.getId(),captor.getValue().getId());
        assertEquals("function passed correctly to dao",role.getFunction(),captor.getValue().getFunction());
    }


    @Ignore
    @Test //todo
    public void postRole_alreadyExists() throws Exception {

    }


}
