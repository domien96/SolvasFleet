package solvas.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import solvas.models.User;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.ApiUser;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.UserRestController;
import solvas.rest.service.AbstractService;
import solvas.rest.service.UserService;
import solvas.rest.utils.JsonListWrapper;

import static io.github.benas.randombeans.api.EnhancedRandom.randomSetOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the UserRestController
 * It checks HTTP responses and calls to the VehicleDao
 */
@RunWith(MockitoJUnitRunner.class)
public class UserRestControllerTest extends AbstractRestControllerTest<User,ApiUser>{
    @Mock
    private UserService userService;

    public UserRestControllerTest() {
        super(ApiUser.class);
    }

    /**
     * Method to check if json has the correct attributes
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
        return "/users";
    }

    @Override
    public String getIdUrl() {
        return "/users/10";
    }

}
