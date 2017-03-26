package solvas.rest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import solvas.models.Role;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.RoleDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.RoleAbstractMapper;
import solvas.rest.api.models.ApiRole;
import solvas.rest.controller.RoleRestController;

import java.time.LocalDateTime;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

/**
 * Integration tests of the RoleRestController
 * It checks HTTP responses and calls to the RoleDao
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RoleRestController.class)
public class RoleRestControllerTest extends AbstractControllerTest<Role, ApiRole> {

    @MockBean
    private RoleDao roleDaoMock;

    @MockBean
    private RoleAbstractMapper roleMapperMock;

    private ApiRole apiRole;

    /**
     * Setup of mockMVC
     * currently provides one random role object and its json representation
     */
    @Before
    public void setUp() {
        apiRole = random(ApiRole.class);
        apiRole.setId(500);
        apiRole.setStartDate(LocalDateTime.now());
        apiRole.setEndDate(null);
    }

    @Override
    protected Dao<Role> getMockDao() {
        return roleDaoMock;
    }

    @Override
    protected AbstractMapper<Role, ApiRole> getMockMapper() {
        return roleMapperMock;
    }

    @Override
    protected ApiRole getValidModel() {
        return apiRole;
    }

    @Override
    protected String getBaseUrl() {
        return "/roles/";
    }

    @Override
    protected Class<ApiRole> getApiClass() {
        return ApiRole.class;
    }

    @Override
    protected Class<Role> getModelClass() {
        return Role.class;
    }
}