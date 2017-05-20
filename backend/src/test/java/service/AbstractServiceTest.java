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

import java.util.List;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractServiceTest<T extends Model,E extends ApiModel> {

    private Class<? extends T> clazz1;
    private E apiModel;
    private T model;
    private Class<? extends E> clazz2;
    public AbstractServiceTest(Class<? extends T> clazz1,Class<? extends E> clazz2)
    {
        this.clazz1=clazz1;
        this.clazz2=clazz2;
        apiModel=random(clazz2);
        model=random(clazz1);
    }

    public T getTestModel()
    {
        return model;
    }
    public E getTestApiModel()
    {
        return apiModel;
    }

    protected ArgumentCaptor<? extends T> captor;
    protected abstract AbstractService<T,E> getService();
    protected abstract Dao getDaoMock();
    protected abstract AbstractMapper<T,E> getMapperMock();

    @Before
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        when(getMapperMock().convertToApiModel(any())).thenReturn(getTestApiModel());
        when(getMapperMock().convertToModel(any())).thenReturn(getTestModel());
        captor = ArgumentCaptor.forClass(clazz1);

    }


    @Test
    public void getById() throws EntityNotFoundException {
        assertThat(getService().getById(4),is(getTestApiModel()));
    }

    @Test
    public void create() throws DependantEntityNotFound, EntityNotFoundException {
        getService().create(getTestApiModel());
        verify(getDaoMock()).save(captor.capture());
        assertThat(captor.getValue(),is(getTestModel()));
    }

    @Test
    public void archive() throws EntityNotFoundException, UnarchivableException {
        getService().archive(2);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(getDaoMock()).archive(captor.capture());
        assertThat(captor.getValue(),is(2));
    }

    @Test
    public void destroy() throws EntityNotFoundException, UndeletableException {
        when(getDaoMock().find(anyInt())).thenReturn(getTestModel());
        getService().destroy(3);
        verify(getDaoMock()).destroy(captor.capture());
        assertThat(captor.getValue(),is(getTestModel()));
    }

    @Test
    public void update() throws DependantEntityNotFound, EntityNotFoundException {
        when(getDaoMock().save(getTestModel())).thenReturn( getTestModel());
        getService().update(5,getTestApiModel());
        verify(getDaoMock()).save(captor.capture());
        assertThat(captor.getValue(),is(getTestModel()));
    }

    @Test
    public void count() {
        long count=4;
        when(getDaoMock().count(any(Specification.class))).thenReturn(count);
            assertThat(getService().count(null),is(count));

    }






}
