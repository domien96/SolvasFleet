package solvas.rest.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import solvas.models.Model;
import solvas.persistence.api.Dao;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.Filter;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.exceptions.DependantEntityNotFound;
import solvas.rest.api.models.ApiModel;

import java.util.Collection;
import java.util.HashSet;

/**
 * Abstract Service
 */
public abstract class AbstractService<T extends Model,E extends ApiModel> {


    private Dao<T> modelDao;
    protected AbstractMapper<T,E> mapper;

    public AbstractService(Dao<T> modelDao,AbstractMapper<T,E> mapper)
    {
        this.modelDao=modelDao;
        this.mapper=mapper;
    }

    public E getById(int id) throws EntityNotFoundException {
        T model = modelDao.find(id);
        return mapper.convertToApiModel(model);
    }

    public Page<E> findAll(Pageable pagination, Filter<T> filters)
    {
        return modelDao.findAll(filters,pagination).map(s -> mapper.convertToApiModel(s));
    }

    public long count(Specification<T> spec)
    {
        return modelDao.count(spec);
    }

    public E create(E input) throws DependantEntityNotFound, EntityNotFoundException {
        T model = mapper.convertToModel(input);
        return mapper.convertToApiModel(modelDao.save(model));
    }

    public void destroy(int id) throws EntityNotFoundException {
        modelDao.destroy(modelDao.find(id));
    }

    public E update(int id,E input) throws DependantEntityNotFound, EntityNotFoundException {
        input.setId(id);
        return mapper.convertToApiModel(modelDao.save(mapper.convertToModel(input)));
    }
}
