package solvas.rest;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.ResultActions;
import solvas.models.Role;
import solvas.rest.api.models.ApiRole;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.RoleRestController;
import solvas.rest.service.AbstractService;
import solvas.rest.service.RoleService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Integration tests of the RoleRestController
 * It checks HTTP responses and calls to the RoleDao
 */
@RunWith(MockitoJUnitRunner.class)
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
     * Match jsonmodel with ApiCompany
     * @param res the ResultAction that mockMvc provides.
     * @param role the role we want to compare with the json result
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
        return RestTestFixtures.ROLEROOTURL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.ROLEIDURL;
    }

    @Override
    public ApiRole getTestModel()
    {
        ApiRole role=super.getTestModel();
        role.setEndDate(role.getStartDate().plusDays(20));
        return role;
    }
}
