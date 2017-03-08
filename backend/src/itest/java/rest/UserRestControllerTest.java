package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import matchers.TestMatcher;
import matchers.UserMatcher;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.User;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.user.UserDao;
import solvas.rest.controller.UserRestController;

import java.util.Collections;
import java.util.Random;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class UserRestControllerTest {
    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserRestController controller;

    private MockMvc mockMvc;

    private ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    private TestMatcher<User> matcher=new UserMatcher();

    private User user;

    private String json;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
        user=random(User.class);
        json=new ObjectMapper().writeValueAsString(user);
    }

    @Test
    public void getUserById_notFound() throws Exception {
        when(userDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/users/{id}",10))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getUserById_noError() throws Exception {
       when(userDaoMock.find(anyInt())).thenReturn(user);
       ResultActions resultActions = mockMvc.perform(get("/users/23"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

       matcher.jsonMatcher(resultActions,user);
    }

    @Test
    public void getUsers_noError() throws Exception {
        when(userDaoMock.findAll()).thenReturn(Collections.singletonList(user));
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void postUser_noError() throws Exception {
        when(userDaoMock.save(any())).thenReturn(user);
        ResultActions resultActions=mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());

        matcher.jsonMatcher(resultActions,user);

        verify(userDaoMock,times(1)).save(captor.capture());

        matcher.performAsserts(user,captor.getValue());
    }

    @Ignore
    @Test
    public void postUser_alreadyExists() throws Exception {
        //todo
    }


    @Test
    public void putUser_noError() throws Exception
    {
        when(userDaoMock.save(any())).thenReturn(user);
        ResultActions resultActions=
                mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk());

        matcher.jsonMatcher(resultActions,user);

        verify(userDaoMock,times(1)).save(captor.capture());
        matcher.performAsserts(user,captor.getValue());
    }

    @Test
    public void putUser_notFound() throws Exception
    {
        when(userDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }





    @Ignore //on hold
    @Test
    public void destroyUser_noError() throws Exception {
        when(userDaoMock.destroy(any())).thenReturn(user);
        mockMvc.perform(delete("/users").contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
    }
    @Ignore //on hold
    @Test
    public void destroyUser_notFound() throws Exception {
        when(userDaoMock.destroy(any())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete("/users").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }


}
