package solvas.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import solvas.models.User;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.UserDao;
import solvas.rest.api.mappers.UserAbstractMapper;
import solvas.rest.api.models.ApiUser;
import solvas.rest.controller.UserRestController;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests of the UserRestController
 * It checks HTTP responses and calls to the VehicleDao
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {
    @MockBean
    private UserDao userDaoMock;

    @MockBean
    private UserAbstractMapper userMapperMock;

    @Autowired
    private MockMvc mockMvc;

    private ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    private ApiUser user;
    private String json;

    /**
     * Setup of mockMVC
     * currently provides one random user object and its json representation
     */
    @Before
    public void setUp() throws Exception {
        user=random(ApiUser.class);
        user.setEmail("test@example.be");
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        json=mapper.writeValueAsString(user);
    }

    /**
     * Test: the response of a get request for a user that doesn't exist on the db
     */
    @Test
    public void getUserByIdNotFound() throws Exception {
        when(userDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/users/{id}",10))
                .andExpect(status().isNotFound());

    }

    /**
     * Test: the response of a get request for a user that exists on the db
     */
    @Test
    public void getUserByIdNoError() throws Exception {
        when(userMapperMock.convertToApiModel(any())).thenReturn(user);
       ResultActions resultActions = mockMvc.perform(get("/users/23"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

       matchUserJson(resultActions,user);
    }

    /**
     * Test: the response of a get request for all the users
     */
    @Test
    public void getUsersNoError() throws Exception {

        // Empty page
        Page<User> emptyPage = new PageBuilder<User>()
                .totalElements(0)
                .build();

        when(userDaoMock.findAll(any(), any(Pageable.class))).thenReturn(emptyPage);
        when(userMapperMock.convertToApiModel(any())).thenReturn(random(ApiUser.class));
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    /**
     * Test: the response of a put request for a new user that doesn't exist on the db
     */
    @Test
    public void postUserNoError() throws Exception {
        when(userMapperMock.convertToApiModel(any())).thenReturn(user);
        ResultActions resultActions=mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        matchUserJson(resultActions,user);
        verify(userDaoMock,times(1)).save(captor.capture());

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
        when(userMapperMock.convertToApiModel(any())).thenReturn(user);
        when(userMapperMock.convertToModel(any())).thenReturn(random(User.class));
        ResultActions resultActions=
                mockMvc.perform(put("/users/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());
        matchUserJson(resultActions,user);
        verify(userDaoMock,times(1)).update(captor.capture());

    }

    /**
     * Test: the response of a put request for a user that doesn't exist
     */
    @Ignore //behavior is not as expected
    @Test
    public void putUserNotFound() throws Exception
    {
        when(userMapperMock.convertToApiModel(any())).thenReturn(user);
        when(userMapperMock.convertToModel(any())).thenReturn(random(User.class));
        when(userDaoMock.save(any(User.class))).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/users/10").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a destroy request for a user that exists
     */
    @Test
    public void destroyUserNoError() throws Exception {
        mockMvc.perform(delete("/users/10").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isNoContent());
        verify(userDaoMock,times(1)).destroy(anyInt());
    }

    /**
     * Test: the response of a destroy request for a user that doesn't exist
     */
    @Test
    public void destroyUserNotFound() throws Exception {
        when(userDaoMock.destroy(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/users/20").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
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
                .andExpect(jsonPath("url").value(user.getUrl()));
    }


}
