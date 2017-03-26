package solvas.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import solvas.models.Role;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiRole;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.RoleRestController;
import solvas.rest.service.AbstractService;
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
public class RoleRestControllerTest extends AbstractRestControllerTest<Role,ApiRole> {

    @Mock
    private RoleService roleService;

    public RoleRestControllerTest() {
        super(ApiRole.class);
    }


    /**
     * Method to check if json has the correct attributes
     */
    public void matchJsonModel(ResultActions res,ApiRole role) throws Exception {
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
        return new RoleRestController(roleService);
    }

    @Override
    protected AbstractService<Role, ApiRole> getService() {
        return roleService;
    }

    @Override
    protected String getBaseUrl() {
        return "/roles";
    }

    @Override
    public String getIdUrl() {
        return "/roles/11";
    }

    @Override
    public ApiRole getTestModel()
    {
        ApiRole role=super.getTestModel();
        role.setEndDate(role.getStartDate().plusDays(20));
        return role;
    }
}
