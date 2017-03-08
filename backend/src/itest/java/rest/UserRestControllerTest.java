package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.User;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.user.UserDao;
import solvas.rest.controller.UserRestController;

import java.util.Collections;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserRestControllerTest {
    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private UserRestController controller;

    private MockMvc mockMvc;

    private ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    private User createDefaultUser() {
        User u= new User("John","Doe","john@doe.com","secret","john.doe");
        u.setId(new Random().nextInt(100));
        return u;
    }


    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getUserById_notFound() throws Exception {
        when(userDaoMock.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/users/{id}",10))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());

    }

    @Test
    public void getUserById_noError() throws Exception {
        User user = createDefaultUser();
       when(userDaoMock.find(anyInt())).thenReturn(user);
       mockMvc.perform(get("/users/23"))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
               .andExpect(jsonPath("id").value(user.getId()));
    }

    @Test
    public void getUsers_noError() throws Exception {
        when(userDaoMock.findAll()).thenReturn(Collections.singletonList(createDefaultUser()));
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }


    @Test
    public void postUser_noError() throws Exception {
        User newUser = createDefaultUser();
        String json = new ObjectMapper().writeValueAsString(newUser);
        when(userDaoMock.save(any())).thenReturn(newUser);
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(newUser.getId()))
                .andExpect(jsonPath("firstName").value(newUser.getFirstName()));

        verify(userDaoMock,times(1)).save(captor.capture());

        assertEquals("id sent to dao correctly",newUser.getId(),captor.getValue().getId());
        assertEquals("firstName sent to dao correctly",newUser.getFirstName(),captor.getValue().getFirstName());
    }

    @Ignore
    @Test
    public void postUser_alreadyExists() throws Exception {
        //todo
    }


    @Test
    public void putUser_noError() throws Exception
    {
        User update = createDefaultUser();
        String json=new ObjectMapper().writeValueAsString(update);


        when(userDaoMock.save(any())).thenReturn(update);
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(update.getId()))
                .andExpect(jsonPath("firstName").value(update.getFirstName()))
                .andExpect(jsonPath("email").value(update.getEmail()));


        verify(userDaoMock,times(1)).save(captor.capture());
        assertEquals("id to DAO",update.getId(),captor.getValue().getId());
        assertEquals("firstName to DAO",update.getFirstName(),captor.getValue().getFirstName());
        assertEquals("email to DAO",update.getEmail(),captor.getValue().getEmail());
    }

    @Test
    public void putUser_notFound() throws Exception
    {
        when(userDaoMock.save(any())).thenThrow(new EntityNotFoundException());
        String json = new ObjectMapper().writeValueAsString(createDefaultUser());
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON_UTF8).content(json))
                .andExpect(status().isNotFound());
    }





    @Ignore //on hold
    @Test
    public void destroyUser_noError() throws Exception {
        User user = createDefaultUser();
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
