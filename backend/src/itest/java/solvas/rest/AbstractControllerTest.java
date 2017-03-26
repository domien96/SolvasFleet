package solvas.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import solvas.models.Model;
import solvas.persistence.api.Dao;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.models.ApiModel;
import solvas.rest.utils.JsonListWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomCollectionOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Common methods and utilities to unit test the controllers.
 *
 * @param <T> The type of the domain model.
 * @param <A> The type of the API model.
 *
 * @author Niko Strijbol
 */
public abstract class AbstractControllerTest<T extends Model, A extends ApiModel> {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private Dao<T> mockDao;
    private AbstractMapper<T, A> mockMapper;

    private Class<T> modelClass;
    private Class<A> apiClass;

    private ArgumentCaptor<T> captor;

    /**
     * Set up some fields.
     */
    @Before
    public void setUpFields() {
        mockDao = getMockDao();
        mockMapper = getMockMapper();
        modelClass = getModelClass();
        apiClass = getApiClass();
        captor = ArgumentCaptor.forClass(modelClass);
    }

    /**
     * @return A mock of the DAO to use.
     */
    protected abstract Dao<T> getMockDao();

    /**
     * @return A mock of the mapper to use.
     */
    protected abstract AbstractMapper<T, A> getMockMapper();

    /**
     * @return Instance of a valid model.
     */
    protected abstract A getValidModel();

    /**
     * @return The base URL of the controller.
     */
    protected abstract String getBaseUrl();

    /**
     * @return The class of the API model.
     */
    protected abstract Class<A> getApiClass();

    /**
     * @return The class of the domain model.
     */
    protected abstract Class<T> getModelClass();

    private String getValidString() throws JsonProcessingException {
        return mapper.writeValueAsString(getValidModel());
    }

    /**
     * Test: the response of a get request for a company that exists on the db
     */
    @Test
    public void getByIdWithoutError() throws Exception {
        when(mockMapper.convertToApiModel(any(modelClass))).thenReturn(getValidModel());
        mockMvc.perform(get(getBaseUrl() + getValidModel().getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(getValidString()))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Test: the response of a get request for a company that doesn't exist on the db
     */
    @Test
    public void getByIdNotFound() throws Exception {
        when(mockDao.find(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(get(getBaseUrl() + getValidModel().getId() + 1))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a get request for all the companies.
     */
    @Test
    public void getEmptyListNoError() throws Exception {

        // Empty page
        Page<T> emptyPage = new PageBuilder<T>()
                .totalElements(0)
                .build();

        String result = mapper.writeValueAsString(JsonListWrapper.forPageable(emptyPage));

        when(mockDao.findAll(any(), any(Pageable.class))).thenReturn(emptyPage);
        when(mockMapper.convertToApiModel(any())).thenReturn(random(apiClass));

        mockMvc.perform(get(getBaseUrl()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(result));
    }

    /**
     * Test: the response of a get request for all the companies.
     */
    @Test
    public void get10ObjectsNoError() throws Exception {

        List<T> companies = new ArrayList<>(randomCollectionOf(10, modelClass));

        // Empty page
        Page<T> page = new PageBuilder<T>()
                .elements(companies)
                .totalElements(companies.size())
                .build();

        Map<T, A> conversion = new HashMap<>();
        companies.forEach(c -> conversion.put(c, random(apiClass)));

        Page<A> converted = page.map(conversion::get);

        String result = mapper.writeValueAsString(JsonListWrapper.forPageable(converted));

        when(mockDao.findAll(any(), any(Pageable.class))).thenReturn(page);
        when(mockMapper.convertToApiModel(any(modelClass))).then(invocation -> {
            return conversion.get((T) invocation.getArguments()[0]);
        });

        mockMvc.perform(get(getBaseUrl()))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(result));
    }

    /**
     * Test: the response of a post request for a new company that doesn't exist on the db
     */
    @Test
    public void postNoError() throws Exception {
        when(mockMapper.convertToApiModel(any())).thenReturn(getValidModel());

        mockMvc.perform(post(getBaseUrl())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getValidString()))
                .andExpect(status().isOk())
                .andExpect(content().json(getValidString()));

        verify(mockDao, times(1)).save(captor.capture());
    }

    /**
     * Test: the response of a put request for a role that exists on the db
     */
    @Test
    public void putNoError() throws Exception {

        // Return anything
        when(mockDao.find(anyInt())).thenReturn(random(modelClass));
        // Return the correct company.
        when(mockMapper.convertToApiModel(any())).thenReturn(getValidModel());
        when(mockMapper.convertToModel(any())).thenReturn(random(modelClass));

        mockMvc.perform(put(getBaseUrl() + getValidModel().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getValidString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(getValidString()));

        verify(mockDao, times(1)).update(captor.capture());
    }

    /**
     * Test: the response of a post request for a role that doesn't exist on the db (error)
     */
    @Test
    public void putNotFound() throws Exception {
        when(mockDao.update(any())).thenThrow(new EntityNotFoundException());

        mockMvc.perform(put(getBaseUrl() + getValidModel().getId() + 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(getValidString()))
                .andExpect(status().isNotFound());
    }

    /**
     * Test: the response of a destroy requests for a company that exists
     */
    @Test
    public void destroyNoError() throws Exception {
        when(mockDao.destroy(any())).thenReturn(random(modelClass));
        mockMvc.perform(delete(getBaseUrl() + getValidModel().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNoContent());
        verify(mockDao, times(1)).destroy(anyInt());
    }

    /**
     * Test: the response of a destroy requests for a company that doesn't exists
     */
    @Test
    public void destroyNotFound() throws Exception {
        when(mockDao.destroy(anyInt())).thenThrow(new EntityNotFoundException());
        mockMvc.perform(delete(getBaseUrl() + getValidModel().getId() + 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }
}