package solvas.rest;

import org.mockito.Mock;
import solvas.rest.api.models.ApiUser;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.UserRestController;
import solvas.service.AbstractService;
import solvas.service.UserService;
import solvas.service.models.User;

/**
 * Integration tests of the UserRestController
 * It checks HTTP responses
 */
public class UserRestControllerTest extends AbstractRestControllerTest<User,ApiUser>{
    @Mock
    private UserService userService;


    /**
     * Constructor for specific UserController tests
     */
    public UserRestControllerTest() {
        super(ApiUser.class);
    }

    /**
     * @return the user rest controller
     */
    @Override
    AbstractRestController getController() {
        return new UserRestController(userService);
    }

    @Override
    ApiUser getTestModel()
    {
        ApiUser apiUser=super.getTestModel();
        apiUser.setEmail("valid@email.com");
        return apiUser;
    }

    @Override
    protected AbstractService<User, ApiUser> getService() {
        return userService;
    }

    @Override
    protected String getBaseUrl() {
        return RestTestFixtures.USER_ROOT_URL;
    }

    @Override
    public String getIdUrl() {
        return RestTestFixtures.USER_ID_URL;
    }

}
