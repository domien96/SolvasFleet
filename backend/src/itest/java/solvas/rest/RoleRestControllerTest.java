package solvas.rest;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import solvas.rest.api.models.ApiRole;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.RoleRestController;
import solvas.service.AbstractService;
import solvas.service.RoleService;
import solvas.service.exceptions.UndeletableException;
import solvas.service.models.Role;

import java.util.Arrays;
import java.util.HashSet;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests of the RoleRestController
 * It checks HTTP responses
 */
public class RoleRestControllerTest extends AbstractRestControllerTest<Role,ApiRole> {


    @Mock
    private RoleService roleService;


    /**
     * Constructor for specific CompanyController tests
     */
    public RoleRestControllerTest() {
        super(ApiRole.class);
    }

    /**
     * @return the role rest controller
     */
    @Override
    AbstractRestController getController() {
        return new RoleRestController(roleService);
    }

    @Override
    protected AbstractService<Role, ApiRole> getService() {
        return roleService;
    }

    @Override
    protected String getBaseUrl() {
        return RestTestFixtures.ROLE_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.ROLE_ID_URL;
    }

    @Override
    public ApiRole getTestModel()
    {
        ApiRole role = super.getTestModel();
        role.setPermissions(new HashSet<>(Arrays.asList("a","b")));
        return role;
    }

    /**
     * Test: Conflict response when delete isn't possible (delete is only possible in the rolerestcontroller)
     */
    @Test
    public void undeletableExceptionHandler() throws Exception {
        doThrow(new UndeletableException()).when(getService()).destroy(anyInt());
        getMockMvc().perform(delete(getIdUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isConflict());
    }

    /**
     * Test: updating the permissions of a role
     */
    @Test
    public void putPermissions() throws Exception {
        when(roleService.update(anyInt(), Matchers.any())).thenReturn(getTestModel());
        getMockMvc()
                .perform(put(RestTestFixtures.ROLE_PERMISSION_URL)
                        .content("[\"a\",\"b\"]")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(getTestJson()));
    }
}
