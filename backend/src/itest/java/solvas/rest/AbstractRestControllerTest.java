package solvas.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.service.models.Model;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;
import solvas.rest.controller.AbstractRestController;
import solvas.service.AbstractService;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Abstract class of the restcontrollers
 * @param <E> The ApiModel
 * @param <T> The Model
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractRestControllerTest<T extends Model,E extends ApiModel> {
     abstract AbstractRestController getController();
     private E apiModel;
     private Class<? extends E> clazz;

    /**
     * Abstract test constructor
     * @param clazz the class we want to make random objects of.
     */
    AbstractRestControllerTest(Class<?extends E> clazz)
     {
         this.clazz=clazz;
     }

    /**
     * Create a random model and its json representation
     */
     @Before
     public void config() throws JsonProcessingException {
         apiModel=random(clazz);
     }

    /**
     * Provides the Rest MVC mock
     * @return Rest MockMVC
     */
    MockMvc getMockMvc()
    {
        return MockMvcBuilders.standaloneSetup(getController()).build();
    }

    /**
     * Provides a random test model
     */
     E getTestModel()
    {
        return apiModel;
    }

    /**
     * Provides the json representation of the random test model
     */
     String getTestJson() throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(getTestModel());
    }

    /**
     * Get the specific 'mocked' service
     */
    protected abstract AbstractService<T,E> getService();

    /**
     * Get the base url (eg /companies)
     */
    protected abstract String getBaseUrl();

    /**
     * Get the url for a specified id (eg /companies/13)
     */
    public abstract String getIdUrl();

    /**
     * Match jsonmodel with apimodel (todo: simplify?)
     * @param res the resultaction that mockMvc provides.
     * @param model the model we want to compare with the json result
     */
    public abstract void matchJsonModel(ResultActions res, E model) throws Exception;












    /*Tests*/


    /**
     * Test: the response of a get request for a model that exists on the db
     */
    @Test
    public void getModelByIdNoError() throws Exception {
        when(getService().getById(anyInt())).thenReturn(getTestModel());
        ResultActions res =getMockMvc().perform(get(getIdUrl()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());
        matchJsonModel(res,getTestModel());
    }

    /**
     * Test: the response of a get request for a model that doesn't exist on the db
     */
    @Test
    public void getModelByIdNotFound() throws Exception {
        when(getService().getById(anyInt())).thenThrow(new EntityNotFoundException());
        getMockMvc().perform(get(getIdUrl()))
                .andExpect(status().isNotFound());
    }

    @Ignore //TODO
    @Test
    public void getModels() throws Exception {
    }

    /**
     * Test: the response of a post request for a new model that doesn't exist on the db
     */
    @Test
    public void postModelNoError() throws Exception {
        when(getService().create(any())).thenReturn(getTestModel());
        ResultActions resultActions=
                getMockMvc().perform(post(getBaseUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                        .andExpect(status().isOk());
        matchJsonModel(resultActions,getTestModel());
    }

    /**
     * Test: the response of a put request for a model that exists on the db
     */
    @Test
    public void putModelNoError() throws Exception {
        when(getService().update(anyInt(),any())).thenReturn(getTestModel());
        ResultActions resultActions =
                getMockMvc().perform(put(getIdUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matchJsonModel(resultActions,getTestModel());
    }

    /**
     * Test: the response of a put request for a model that doesn't exist on the db (error)
     */
    @Test
    public void putModelNotFound() throws Exception {
        when(getService().update(anyInt(),any())).thenThrow(new EntityNotFoundException());
        getMockMvc().perform(put(getIdUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a archive request for a model that exists
     */
    @Test
    public void archiveModelNoError() throws Exception {
        getMockMvc().perform(delete(getIdUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNoContent());
    }

    /**
     * Test: the response of a archive request for a model that doesn't exists
     */
    @Test
    public void archiveModelNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(getService()).archive(anyInt());
        getMockMvc().perform(delete(getIdUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNotFound());
    }


}
