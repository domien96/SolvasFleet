package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
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

import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleRestControllerTest {
    @InjectMocks
    private RoleRestController roleRestController;

    @Mock
    private RoleDao roleDaoMock;

    private MockMvc mockMvc;

    private TestObjectProvider testProvider=new TestObjectProvider();

    private Role role;
    private List<Role> roles;

    @Before
    public void setUp()  {
        role=testProvider.getRole();
        roles=testProvider.getRoles();
        MockitoAnnotations.initMocks(this);
        mockMvc= MockMvcBuilders.standaloneSetup(roleRestController).build();
    }

    @Test
    public void getRoleById_noError() throws Exception {
        Role role = new Role(null,"admin",null,null,null,"test.be");
        role.setId(1);
        when(roleDaoMock.find(anyInt())).thenReturn(role);
        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("id").value(1));
    }

    @Test
    public void getRoleById_notFound() throws Exception {
        when(roleDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/roles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRoles_noError() throws Exception {
        when(roleDaoMock.findAll()).thenReturn(roles);
        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void putRoles_noError() throws Exception {
        mockMvc.perform(put("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(""))
                .andExpect(status().isOk());
        //todo Verificatie actie naar dao
    }

    @Test
    public void putRoles_alreadyExists() throws Exception {
        when(roleDaoMock.find(anyInt())).thenReturn(role);
        mockMvc.perform(put("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(""));
    //            .andExpect(status().i) welke response?
    }


}
