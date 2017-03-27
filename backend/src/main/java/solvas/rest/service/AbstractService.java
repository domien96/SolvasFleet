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

/**
 * Abstract Service
 */
public abstract class AbstractService<T extends Model,E extends ApiModel> {


    private Dao<T> modelDao;
    protected AbstractMapper<T,E> mapper;

    /**
     * Contruct an abstractservice
     * @param modelDao the DAO of the model
     * @param mapper the mapper between the apimodel and the model
     */
    public AbstractService(Dao<T> modelDao,AbstractMapper<T,E> mapper)
    {
        this.modelDao=modelDao;
        this.mapper=mapper;
    }

    /**
     * Getting the model from the database, using the server
     * @param id the id of the model that you want to get
     * @return the ApiModel
     * @throws EntityNotFoundException in case the model was not found
     */
    public E getById(int id) throws EntityNotFoundException {
        T model = modelDao.find(id);
        return mapper.convertToApiModel(model);
    }

    /**
     * Finding all the models
     * @param pagination the pagination, provided by frontend
     * @param filters the filters, provided by frontend
     */
    public Page<E> findAll(Pageable pagination, Filter<T> filters)
    {
        return modelDao.findAll(filters,pagination).map(s -> mapper.convertToApiModel(s));
    }

    /**
     * the amount of models in the db that have several specifications
     * @param spec the specifications/filters
     */
    public long count(Specification<T> spec)
    {
        return modelDao.count(spec);
    }

    /**
     * Create a model and store it in the database
     * @param input the model we want to create
     */
    public E create(E input) throws DependantEntityNotFound, EntityNotFoundException {
        T model = mapper.convertToModel(input);
        return mapper.convertToApiModel(modelDao.save(model));
    }

    /**
     * Destroy a model from the database
     * @param id the id of the entity we want to destroy (or archive)
     */
    public void destroy(int id) throws EntityNotFoundException {
        modelDao.destroy(modelDao.find(id));
    }

    /**
     * Update a model on the database
     * @param id the id of the model we want to update
     * @param input the model we want to update
     */
    public E update(int id,E input) throws DependantEntityNotFound, EntityNotFoundException {
        input.setId(id);
        return mapper.convertToApiModel(modelDao.save(mapper.convertToModel(input)));
    }
}
