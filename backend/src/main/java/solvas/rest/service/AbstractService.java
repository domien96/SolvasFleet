package solvas.rest.service;

import solvas.models.Model;
import solvas.persistence.api.Dao;
import solvas.persistence.api.Filter;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.models.ApiModel;
import solvas.rest.query.Pageable;
import solvas.rest.utils.JsonListWrapper;

import java.util.Collection;
import java.util.HashSet;

/**
 * Abstract Service
 */
public abstract class AbstractService<T extends Model,E extends ApiModel> {


    protected Dao<T> modelDao;
    protected AbstractMapper<T,E> mapper;

    public AbstractService(Dao<T> modelDao,AbstractMapper<T,E> mapper)
    {
        this.modelDao=modelDao;
        this.mapper=mapper;
    }

    public E getById(int id)
    {
        T model = modelDao.find(id);
        return mapper.convertToApiModel(model);
    }

    public Collection<E> findAll(Pageable pageable, Filter<T> filters)
    {
        Collection<E> collection = new HashSet<>();
        for (T item : modelDao.findAll(pageable, filters)) {
            collection.add(mapper.convertToApiModel(item));
        }
        return collection;
    }

    public JsonListWrapper<E> findAndWrap(Pageable pageable, Filter<T> filters)
    {
        Collection<E> collection=findAll(pageable, filters);
        JsonListWrapper<E> wrapper = new JsonListWrapper<>(collection);
        wrapper.put("limit", pageable.getLimit());
        wrapper.put("offset", pageable.getLimit() * pageable.getPage());
        wrapper.put("total", modelDao.count(filters));
        return wrapper;
    }

    public E create(E input)
    {
        T model = mapper.convertToEmptyModel(input);
        return mapper.convertToApiModel(modelDao.create(model));
    }

    public void destroy(int id)
    {
        modelDao.destroy(modelDao.find(id));
    }

    public E update(int id,E input)
    {
        input.setId(id);
        return mapper.convertToApiModel(modelDao.update(mapper.convertToModel(input,modelDao.find(id))));
    }
}
