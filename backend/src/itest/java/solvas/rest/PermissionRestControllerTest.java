package solvas.rest;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiPermission;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.PermissionRestController;
import solvas.service.AbstractService;
import solvas.service.PermissionService;
import solvas.service.models.Permission;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests of the PermissionRestController
 * It checks HTTP responses
 */
public class PermissionRestControllerTest extends AbstractBasicRestControllerTest<Permission,ApiPermission> {
    @Mock
    private PermissionService service;

    /**
     * PermissionRestControllerTest constructor
     */
    public PermissionRestControllerTest() {
        super(ApiPermission.class);
    }

    @Override
    AbstractRestController getController() {
        return new PermissionRestController(service);
    }

    @Override
    protected AbstractService<Permission, ApiPermission> getService() {
        return service;
    }

    /**
     * Test: listing all the available permissions
     */
    @Test
    public void listAll() throws Exception {
        when(getService().findAll()).thenReturn((List<ApiPermission>) getTestModelList());
        getMockMvc().perform(get(RestTestFixtures.PERMISSION_ROOT_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data", Matchers.hasToString(getObjectMapper().writeValueAsString(getTestModelList()))));
    }

    /**
     * Test: listing the permissions of a role
     */
    @Test
    public void listForRole() throws Exception {
        when(getService().findAll(any(),any())).thenReturn(new PageImpl(getTestModelList()));
        getMockMvc().perform(get(RestTestFixtures.ROLE_PERMISSION_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("data",Matchers.hasToString(getObjectMapper().writeValueAsString(getTestModelList()))));
    }

    /**
     * Test: NotFound response when role doesn't exist
     */
    @Test
    public void listForRoleNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(service).filterOnRole(any(),anyInt());
        getMockMvc().perform(get(RestTestFixtures.ROLE_PERMISSION_URL))
                .andExpect(status().isNotFound());
    }
}
