package service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.jpa.domain.Specification;
import solvas.persistence.api.Dao;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiModel;
import solvas.service.AbstractService;
import solvas.service.exceptions.UnarchivableException;
import solvas.service.exceptions.UndeletableException;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Model;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Abstract class for service tests.
 *
 * @param <T> The model.
 * @param <E> The api model.
 */
@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractServiceTest<T extends Model, E extends ApiModel> {

    protected ArgumentCaptor<? extends T> captor;
    private Class<? extends T> clazz1;
    private E apiModel;
    private T model;

    /**
     * Construct a service test.
     *
     * @param modelClass The class of the model.
     * @param apiModelClass The class of the API model.
     */
    public AbstractServiceTest(Class<? extends T> modelClass, Class<? extends E> apiModelClass) {
        this.clazz1 = modelClass;
        apiModel = random(apiModelClass);
        model = random(modelClass);
    }

    public T getTestModel() {
        return model;
    }

    public E getTestApiModel() {
        return apiModel;
    }

    protected abstract AbstractService<T, E> getService();

    protected abstract Dao<T> getDaoMock();

    protected abstract AbstractMapper<T, E> getMapperMock();

    @Before
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        when(getMapperMock().convertToApiModel(any())).thenReturn(getTestApiModel());
        when(getMapperMock().convertToModel(any())).thenReturn(getTestModel());
        captor = ArgumentCaptor.forClass(clazz1);
    }

    /**
     * Test get by ID.
     *
     * @throws EntityNotFoundException should not happen
     */
    @Test
    public void getById() throws EntityNotFoundException {
        assertThat(getService().getById(4), is(getTestApiModel()));
    }

    /**
     * Test creation.
     *
     * @throws DependantEntityNotFound should not happen
     * @throws EntityNotFoundException should not happen
     */
    @Test
    public void create() throws DependantEntityNotFound, EntityNotFoundException {
        getService().create(getTestApiModel());
        verify(getDaoMock()).save(captor.capture());
        assertThat(captor.getValue(), is(getTestModel()));
    }

    /**
     * Test archiving.
     *
     * @throws EntityNotFoundException should not happen
     * @throws UnarchivableException should not happen
     */
    @Test
    public void archive() throws EntityNotFoundException, UnarchivableException {
        getService().archive(2);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(getDaoMock()).archive(captor.capture());
        assertThat(captor.getValue(), is(2));
    }

    /**
     * Test destroying.
     *
     * @throws EntityNotFoundException should not happen
     * @throws UndeletableException should not happen
     */
    @Test
    public void destroy() throws EntityNotFoundException, UndeletableException {
        when(getDaoMock().find(anyInt())).thenReturn(getTestModel());
        getService().destroy(3);
        verify(getDaoMock()).destroy(captor.capture());
        assertThat(captor.getValue(), is(getTestModel()));
    }

    /**
     * Test updating.
     *
     * @throws DependantEntityNotFound should not happen
     * @throws EntityNotFoundException should not happen
     */
    @Test
    public void update() throws DependantEntityNotFound, EntityNotFoundException {
        when(getDaoMock().save(getTestModel())).thenReturn(getTestModel());
        getService().update(5, getTestApiModel());
        verify(getDaoMock()).save(captor.capture());
        assertThat(captor.getValue(), is(getTestModel()));
    }

    /**
     * Test counting.
     */
    @Test
    public void count() {
        long count = 4;
        when(getDaoMock().count(any(Specification.class))).thenReturn(count);
        assertThat(getService().count(null), is(count));

    }
}