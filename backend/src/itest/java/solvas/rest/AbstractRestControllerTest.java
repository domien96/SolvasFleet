package solvas.rest;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.TypeMismatchException;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Abstract class of the restcontrollerstest, including several tests
 * @param <E> The ApiModel
 * @param <T> The Model
 */
public abstract class AbstractRestControllerTest<T extends Model,E extends ApiModel> extends AbstractBasicRestControllerTest<T,E> {


    /**
     * Abstract test constructor
     *
     * @param clazz the class we want to make random objects of.
     */
    public AbstractRestControllerTest(Class<? extends E> clazz) {
        super(clazz);
    }

    /**
     * Get the base url (eg /companies)
     */
    protected abstract String getBaseUrl();

    /**
     * Get the url for a specified id (eg /companies/13)
     */
    public abstract String getIdUrl();

    /**
     * Test: the response of a get request for a model that exists on the db
     */
    @Test
    public void getModelByIdNoError() throws Exception {
        when(getService().getById(anyInt())).thenReturn(getTestModel());
        ResultActions res = getMockMvc().perform(get(getIdUrl()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
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

    @Test
    public void getModels() throws Exception {
        when(getService().findAll(any(),any())).thenReturn(new PageImpl(getTestModelList()));
        getMockMvc()
                .perform(get(getBaseUrl()))
                .andExpect(jsonPath("data", Matchers.hasToString(getObjectMapper().writeValueAsString(getTestModelList()))));
    }

    /**
     * Test: the response of a post request for a new model that doesn't exist on the db
         */
    @Test
    public void postModelNoError() throws Exception {
        when(getService().create(any())).thenReturn(getTestModel());
        ResultActions resultActions = getMockMvc()
                        .perform(post(getBaseUrl())
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(getTestJson()))
                        .andExpect(status().isOk());

        matchJsonModel(resultActions,getTestModel());
    }

    /**
     * Test: postrequets returns isConflict when DependantEntityNotFoundException has been thrown
     */
    @Test
    public void postModelConflict() throws Exception {
        when(getService().create(any())).thenThrow(new DependantEntityNotFound("",null));
        getMockMvc()
                .perform(post(getBaseUrl())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getTestJson()))
                .andExpect(status().isConflict());
    }

    /**
     * Test: postrequest returns isNotFound when EntityNotFoundException has been thrown
     */
    @Test
    public void postModelNotFound() throws Exception {
        when(getService().create(any())).thenThrow(new EntityNotFoundException());
        getMockMvc()
                .perform(post(getBaseUrl())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getTestJson()))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a put request for a model that exists on the db
     */
    @Test
    public void putModelNoError() throws Exception {
        when(getService().update(anyInt(), any())).thenReturn(getTestModel());
        ResultActions resultActions =
                getMockMvc()
                        .perform(put(getIdUrl())
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(getTestJson()))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        matchJsonModel(resultActions, getTestModel());
    }

    /**
     * Test: the response of a put request for a model that doesn't exist on the db (error)
     */
    @Test
    public void putModelNotFound() throws Exception {
        when(getService().update(anyInt(),any())).thenThrow(new EntityNotFoundException());
        getMockMvc()
                .perform(put(getIdUrl())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getTestJson()))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: putrequest returns isConflict when DependantEntityNotFoundException has been thrown
     */
    @Test
    public void putModelConflict() throws Exception {
        when(getService().update(anyInt(),any())).thenThrow(new DependantEntityNotFound("",null));
        getMockMvc()
                .perform(put(getIdUrl())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getTestJson()))
                .andExpect(status().isConflict());
    }

    /**
     * Test: the response of a archive request for a model that exists
     */
    @Test
    public void archiveModelNoError() throws Exception {
        getMockMvc()
                .perform(delete(getIdUrl())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getTestJson()))
                .andExpect(status().isNoContent());
    }

    /**
     * Test: the response of a archive request for a model that doesn't exists
     */
    @Test
    public void archiveModelNotFound() throws Exception {
        doThrow(new EntityNotFoundException()).when(getService()).archive(anyInt());
        doThrow(new EntityNotFoundException()).when(getService()).destroy(anyInt());
        getMockMvc()
                .perform(delete(getIdUrl())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(getTestJson()))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: should return isBadRequest when there has been a typemismatchexception
     */
    @Test
    public void typeMisMatchExceptionHandler() throws Exception {
        // random typemismatch arguments
        when(getService().getById(anyInt())).thenThrow(new TypeMismatchException(mock(Matchers.class),null));
        getMockMvc()
                .perform(get(getIdUrl()))
                .andExpect(status().isBadRequest());
    }
}
