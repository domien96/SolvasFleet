package solvas.rest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import solvas.models.User;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.UserDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.UserAbstractMapper;
import solvas.rest.api.models.ApiUser;
import solvas.rest.controller.UserRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;

/**
 * Integration tests of the UserRestController
 * It checks HTTP responses and calls to the VehicleDao
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest extends AbstractControllerTest<User, ApiUser> {

    @MockBean
    private UserDao userDaoMock;

    @MockBean
    private UserAbstractMapper userMapperMock;

    private ApiUser user;

    /**
     * Setup of mockMVC
     * currently provides one random user object and its json representation
     */
    @Before
    public void setUp() throws Exception {
        user = random(ApiUser.class);
        user.setId(500);
        user.setEmail("test@example.be");
    }

    @Override
    protected Dao<User> getMockDao() {
        return userDaoMock;
    }

    @Override
    protected AbstractMapper<User, ApiUser> getMockMapper() {
        return userMapperMock;
    }

    @Override
    protected ApiUser getValidModel() {
        return user;
    }

    @Override
    protected String getBaseUrl() {
        return "/users/";
    }

    @Override
    protected Class<ApiUser> getApiClass() {
        return ApiUser.class;
    }

    @Override
    protected Class<User> getModelClass() {
        return User.class;
    }
}