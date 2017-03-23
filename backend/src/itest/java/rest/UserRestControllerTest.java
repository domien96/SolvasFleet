package rest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import solvas.models.validators.UserValidator;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiUser;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.controller.UserRestController;
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
public class UserRestControllerTest extends AbstractRestControllerTest<ApiUser>{
    @Mock
    private UserValidator userValidator;

    @Mock
    private UserService userService;

    public UserRestControllerTest() {
        super(ApiUser.class);
    }


    /**
     * Setup of mockMVC
     * currently provides one random user object and its json representation
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     * Test: the response of a get request for a user that doesn't exist on the db
     */
    @Test
    public void getUserByIdNotFound() throws Exception {
        when(userService.getById(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc()
                .perform(get(TestFixtures.userIdUrl))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a get request for a user that exists on the db
     */
    @Test
    public void getUserByIdNoError() throws Exception {
        when(userService.getById(anyInt())).thenReturn(getTestModel());
        ResultActions resultActions = getMockMvc()
               .perform(get(TestFixtures.userIdUrl))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        matchUserJson(resultActions,getTestModel());
    }

    /**
     * Test: the response of a get request for all the users
     */
    @Test
    public void getUsersNoError() throws Exception {
        when(userService.findAndWrap(any(),any())).thenReturn(new JsonListWrapper<>(randomSetOf(10,ApiUser.class)));
        getMockMvc()
                .perform(get(TestFixtures.userBaseUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    /**
     * Test: the response of a put request for a new user that doesn't exist on the db
     */
    @Test
    public void postUserNoError() throws Exception {
        when(userService.create(any())).thenReturn(getTestModel());
        ResultActions resultActions=getMockMvc()
                .perform(post(TestFixtures.userBaseUrl).contentType(MediaType.APPLICATION_JSON).content(getTestJson()))
                .andExpect(status().isOk());

        matchUserJson(resultActions,getTestModel());
    }

    /**
     * Test: the response of a post request for a user that already exists  (error)
     */
    @Ignore
    @Test
    public void postUserAlreadyExists() throws Exception {
        //todo
    }


    /**
     * Test: the response of a put request for a user that exists
     */
    @Test
    public void putUserNoError() throws Exception
    {
        when(userService.update(anyInt(),any())).thenReturn(getTestModel());
        ResultActions resultActions=getMockMvc()
                .perform(put(TestFixtures.userIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isOk());
        matchUserJson(resultActions,getTestModel());
    }

    /**
     * Test: the response of a put request for a user that doesn't exist
     */
    @Ignore //behavior is not as expected
    @Test
    public void putUserNotFound() throws Exception
    {

    }

    /**
     * Test: the response of a destroy request for a user that exists
     */
    @Test
    public void destroyUserNoError() throws Exception {
        getMockMvc()
                .perform(delete(TestFixtures.userIdUrl).contentType(MediaType.APPLICATION_JSON).content(getTestJson()))
                .andExpect(status().isOk());
        verify(userService,times(1)).destroy(anyInt());
    }

    /**
     * Test: the response of a destroy request for a user that doesn't exist
     */
    @Test
    public void destroyUserNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(userService).destroy(anyInt());
        getMockMvc()
                .perform(delete(TestFixtures.userIdUrl).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNotFound());
    }

    /**
     * Method that checks the result of the json string that is contained in the HTTP request
     * @param res from MockMVC object
     * @param user User object that should be equal to the object in json representation
     * @throws Exception when mockMVC fails to perform the action or jsonPath fails to retrieve the attribute
     */
    public void matchUserJson(ResultActions res, ApiUser user) throws Exception {
        res.andExpect(jsonPath("id").value(user.getId()))
                .andExpect(jsonPath("firstName").value(user.getFirstName()))
                .andExpect(jsonPath("lastName").value(user.getLastName()))
                .andExpect(jsonPath("email").value(user.getEmail()))
                .andExpect(jsonPath("url").value(user.getUrl()))
                .andExpect(jsonPath("password").value(user.getPassword()));
    }


    @Override
    AbstractRestController getController() {
        return new UserRestController(userService, userValidator);
    }
}
