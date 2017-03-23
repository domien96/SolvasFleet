package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import solvas.models.validators.RoleValidator;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiRole;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.RoleRestController;
import solvas.rest.service.RoleService;
import solvas.rest.utils.JsonListWrapper;

import static io.github.benas.randombeans.api.EnhancedRandom.randomCollectionOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the RoleRestController
 * It checks HTTP responses and calls to the RoleDao
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleRestControllerTest extends AbstractRestControllerTest<ApiRole> {

    @Mock
    private RoleService roleService;
    @Mock
    private RoleValidator roleValidator;

    public RoleRestControllerTest() {
        super(ApiRole.class);
    }

    /**
     * Test: the response of a get request for a user that exists on the db
     */
    @Test
    public void getRoleByIdNoError() throws Exception {
        when(roleService.getById(anyInt())).thenReturn(getTestModel());
        ResultActions resultActions= getMockMvc()
                .perform(get(TestFixtures.roleIdUrl))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        matchRoleJson(resultActions,getTestModel());
    }

    /**
     * Test: the response of a get request for a role that doesn't exist on the db
     */
    @Test
    public void getRoleByIdNotFound() throws Exception {
        when(roleService.getById(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc()
                .perform(get(TestFixtures.roleIdUrl))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a get request for all the roles
     */
    @Test
    public void getRolesNoError() throws Exception {
        when(roleService.findAndWrap(any(),any())).thenReturn(new JsonListWrapper<>(randomCollectionOf(10,ApiRole.class)));
        getMockMvc()
                .perform(get(TestFixtures.roleBaseUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        //todo json expectation
    }

    /**
     * Test: the response of a put request for a role that exists on the db
     */
    @Test
    public void putRoleNoError() throws Exception {
        when(roleService.update(anyInt(),any())).thenReturn(getTestModel());
        ResultActions resultActions=getMockMvc()
                .perform(put(TestFixtures.roleIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matchRoleJson(resultActions,getTestModel());
    }

    /**
     * Test: the response of a put request for role that doesn't exists on the db
     */
    @Test
    public void putRoleNotFound() throws Exception {
        when(roleService.update(anyInt(),any())).thenThrow(new EntityNotFoundException());
        getMockMvc().perform(put(TestFixtures.roleIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a post request for a new role that doesn't exist on the db
     */
    @Test
    public void postRoleNoError() throws Exception {
        when(roleService.create(any())).thenReturn(getTestModel());
        ResultActions resultActions=getMockMvc()
                .perform(post(TestFixtures.roleBaseUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matchRoleJson(resultActions,getTestModel());
    }


    /**
     * Test: the response of a destroy request for a user that exists
     */
    @Test
    public void destroyUser_noError() throws Exception {
        getMockMvc()
                .perform(delete(TestFixtures.roleIdUrl).contentType(MediaType.APPLICATION_JSON).content(getTestJson()))
                .andExpect(status().isOk());
        verify(roleService,times(1)).destroy(anyInt());
    }

    /**
     * Test: the response of a destroy request for a user that doesn't exist
     */
    @Test
    public void destroyUser_notFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(roleService).destroy(anyInt());
        getMockMvc()
                .perform(delete(TestFixtures.roleIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNotFound());
    }

    /**
     * Method to check if json has the correct attributes
     */
    private void matchRoleJson(ResultActions res,ApiRole role) throws Exception {
        res.andExpect(jsonPath("id").value(role.getId()))
                .andExpect(jsonPath("function").value(role.getFunction()))
                .andExpect(jsonPath("url").value(role.getUrl()))
                .andExpect(jsonPath("company").value(role.getCompany()))
                .andExpect(jsonPath("lastUpdatedBy").value(role.getLastUpdatedBy()))
                .andExpect(jsonPath("function").value(role.getFunction()))
                .andExpect(jsonPath("user").value(role.getUser()));
    }


    /**
     * @return the role rest controller
     */
    @Override
    AbstractRestController getController() {
        return new RoleRestController(roleService, roleValidator);
    }
}
