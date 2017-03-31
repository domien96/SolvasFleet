package solvas.rest;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.ResultActions;
import solvas.service.models.User;
import solvas.rest.api.models.ApiUser;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.UserRestController;
import solvas.service.AbstractService;
import solvas.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Integration tests of the UserRestController
 * It checks HTTP responses and calls to the VehicleDao
 */
@RunWith(MockitoJUnitRunner.class)
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
     * Match jsonmodel with ApiCompany
     * @param res the ResultAction that mockMvc provides.
     * @param user the user we want to compare with the json result
     */
    public void matchJsonModel(ResultActions res, ApiUser user) throws Exception {
        res.andExpect(jsonPath("id").value(user.getId()))
                .andExpect(jsonPath("firstName").value(user.getFirstName()))
                .andExpect(jsonPath("lastName").value(user.getLastName()))
                .andExpect(jsonPath("email").value(user.getEmail()))
                .andExpect(jsonPath("url").value(user.getUrl()));
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
