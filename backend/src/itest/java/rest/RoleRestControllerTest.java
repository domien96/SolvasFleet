package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Role;
import solvas.models.validators.RoleValidator;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.RoleDao;
import solvas.rest.api.mappers.RoleAbstractMapper;
import solvas.rest.api.models.ApiRole;
import solvas.rest.controller.RoleRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomCollectionOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the RoleRestController
 * It checks HTTP responses and calls to the RoleDao
 */
public class RoleRestControllerTest {
    @Mock
    private RoleDao roleDaoMock;
    @Mock
    private RoleAbstractMapper roleMapperMock;
    @Mock
    private RoleValidator roleValidatorMock;

    @Mock
    private DaoContext daoContextMock;

    private MockMvc mockMvc;

    private ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);

    private ApiRole apiRole;
    private String json;


    /**
     * Setup of mockMVC
     * currently provides one random role object and its json representation
     */
    @Before
    public void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(daoContextMock.getRoleDao()).thenReturn(roleDaoMock);
        RoleRestController roleRestController=new RoleRestController(daoContextMock,roleMapperMock,roleValidatorMock);

        mockMvc= MockMvcBuilders.standaloneSetup(roleRestController).build();
        apiRole=random(ApiRole.class);
        ObjectMapper mapper=new ObjectMapper();
        mapper.findAndRegisterModules(); //Include module to convert LocaleDateTime objects
        json=mapper.writeValueAsString(apiRole);
    }

    /**
     * Test: the response of a get request for a user that exists on the db
     */
    @Test
    public void getRoleByIdNoError() throws Exception {
        when(roleMapperMock.convertToApiModel(any())).thenReturn(apiRole);
        ResultActions resultActions=
                mockMvc.perform(get("/roles/1"))
                .andExpect(status().isOk())
                        .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matchRoleJson(resultActions,apiRole);
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
        when(roleMapperMock.convertToApiModel(any())).thenReturn(random(ApiRole.class));
        when(roleDaoMock.findAll(any(),any())).thenReturn(randomCollectionOf(10,Role.class));//Voorlopig nodig zodat er 10 aanwezig zijn
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
        when(roleMapperMock.convertToApiModel(any())).thenReturn(apiRole);
        when(roleMapperMock.convertToModel(any())).thenReturn(random(Role.class));
        ResultActions resultActions=
                mockMvc.perform(put("/roles/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matchRoleJson(resultActions,apiRole);


        // Verificatie actie naar dao
        verify(roleDaoMock,times(1)).update(captor.capture());
    }

    /**
     * Test: the response of a put request for role that doesn't exists on the db
     */
    @Ignore //behavior is not as expected
    @Test
    public void putRoleNotFound() throws Exception {
        when(roleDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        when(roleMapperMock.convertToModel(any())).thenReturn(random(Role.class));
        when(roleMapperMock.convertToApiModel(any())).thenReturn(apiRole);
        mockMvc.perform(put("/roles/11").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a post request for a new role that doesn't exist on the db
     */
    @Test
    public void postRoleNoError() throws Exception {
        when(roleMapperMock.convertToApiModel(any())).thenReturn(apiRole);
        when(roleMapperMock.convertToModel(any())).thenReturn(random(Role.class));
        ResultActions resultActions=
                mockMvc.perform(post("/roles").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matchRoleJson(resultActions,apiRole);

        verify(roleDaoMock,times(1)).create(captor.capture());
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
    @Test
    public void destroyUser_noError() throws Exception {
        mockMvc.perform(delete("/roles/10").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
        verify(roleDaoMock,times(1)).destroy(any());
    }

    /**
     * Test: the response of a destroy request for a user that doesn't exist
     */
    @Test
    public void destroyUser_notFound() throws Exception {
        when(roleDaoMock.destroy(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/roles/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    public void matchRoleJson(ResultActions res,ApiRole role) throws Exception {
        res.andExpect(jsonPath("id").value(role.getId()))
                .andExpect(jsonPath("function").value(role.getFunction()))
                .andExpect(jsonPath("url").value(role.getUrl()))
                .andExpect(jsonPath("company").value(role.getCompany()))
                .andExpect(jsonPath("lastUpdatedBy").value(role.getLastUpdatedBy()))
                .andExpect(jsonPath("function").value(role.getFunction()))
                .andExpect(jsonPath("user").value(role.getUser()));
    }
}
