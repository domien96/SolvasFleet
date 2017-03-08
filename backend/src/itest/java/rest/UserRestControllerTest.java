package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import solvas.models.User;
import solvas.persistence.EntityNotFoundException;
import solvas.rest.controller.UserRestController;
import solvas.persistence.user.UserDao;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class UserRestControllerTest {
    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserRestController controller;

    private MockMvc mockMvc;

    private TestObjectProvider testProvider=new TestObjectProvider();

    private User user;

    private List<User> users;

    @Before
    public void setUp() throws Exception
    {
        user=testProvider.getUser();
        users=testProvider.getUsers();
        MockitoAnnotations.initMocks(this);
        mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetUserById_notFound() throws Exception {
        when(userDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/users/{id}",10))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void testGetUserById_noError() throws Exception {
       when(userDaoMock.find(anyInt())).thenReturn(user);
       mockMvc.perform(get("/users/{id}",3).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(content().string(""));
    }

    @Test
    public void getUsers_noError() throws Exception {
        when(userDaoMock.findAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
              //  .andExpect(content().string(json(users)));
    }


    @Test
    public void postUser() throws Exception {
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON).content(""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
        //todo verify(userDaoMock.save(user1));
    }

    @Ignore //on hold
    @Test
    public void putUser() throws Exception
    {
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
        //todo verify(userDaoMock.save(newUser));
    }
    @Ignore //on hold
    @Test
    public void deleteUser_noError() throws Exception {
        when(userDaoMock.destroy(any())).thenReturn(user);
        mockMvc.perform(delete("/users").contentType(MediaType.APPLICATION_JSON).content(""))
                .andExpect(status().isOk());
    }
    @Test
    public void testDeleteUser_notFound() throws Exception {

    }


}
