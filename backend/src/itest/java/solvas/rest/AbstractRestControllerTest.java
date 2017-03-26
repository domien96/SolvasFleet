package solvas.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import solvas.models.Model;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;
import solvas.rest.controller.AbstractRestController;
import solvas.rest.service.AbstractService;

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
 * @param <E> The apimodel
 */
public abstract class AbstractRestControllerTest<T extends Model,E extends ApiModel> {
     abstract AbstractRestController getController();
     private E apiModel;
     private Class<? extends E> clazz;
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

    protected abstract AbstractService<T,E> getService();

    protected abstract String getBaseUrl();

    public abstract String getIdUrl();

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
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a destroy requests for a model that exists
     */
    @Test
    public void destroyCompanyNoError() throws Exception {
        getMockMvc().perform(delete(getIdUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isOk());
    }

    /**
     * Test: the response of a destroy requests for a model that doesn't exists
     */
    @Test
    public void destroyCompanyNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(getService()).destroy(anyInt());
        getMockMvc().perform(delete(getIdUrl()).contentType(MediaType.APPLICATION_JSON_UTF8).content(getTestJson()))
                .andExpect(status().isNotFound());
    }


}
