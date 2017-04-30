package solvas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import solvas.persistence.api.DaoContext;
import solvas.service.models.Model;
import solvas.persistence.api.Dao;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.Filter;
import solvas.rest.api.models.ApiModel;
import solvas.service.exceptions.UndeletableException;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract Service class
 * @param <T> The class of the Model
 * @param <E> The class of the ApiModel
 */
@Transactional
public abstract class AbstractService<T extends Model,E extends ApiModel> {


    protected Dao<T> modelDao;
    protected AbstractMapper<T,E> mapper;
    protected DaoContext context=null;

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
     * Contruct an abstractservice
     * @param modelDao the DAO of the model
     * @param mapper the mapper between the apimodel and the model
     */
    public AbstractService(Dao<T> modelDao,DaoContext context,AbstractMapper<T,E> mapper)
    {
        this.modelDao=modelDao;
        this.mapper=mapper;
        this.context=context;
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
     * @param filters the filters, provided by frontend.
     * @return the paged representation of the specific models.
     */
    public Page<E> findAll(Pageable pagination, Filter<T> filters) {
        return modelDao.findAll(filters,pagination).map(s -> mapper.convertToApiModel(s));
    }

    /**
     * Finding all the models
     * @param filters the filters, provided by frontend.
     * @return the filtered representation of the specific models.
     */
    public List<E> findAll(Filter<T> filters) {
        return modelDao.findAll(filters).stream().map(s -> mapper.convertToApiModel(s)).collect(Collectors.toList());
    }

    /**
     * @param spec the specifications/filters
     * @return the amount of models with specifications
     */
    public long count(Specification<T> spec)
    {
        return modelDao.count(spec);
    }

    /**
     * Create a model and store it in the database
     * @param input the model we want to create
     * @return the api representation of the stored model
     */
    public E create(E input) throws DependantEntityNotFound, EntityNotFoundException {
        T model = mapper.convertToModel(input);
        return mapper.convertToApiModel(modelDao.save(model));
    }

    /**
     * Archive a model on the database
     * @param id the id of the entity we want to archive
     */
    public void archive(int id) throws EntityNotFoundException {
        modelDao.archive(id);
    }

    /**
     * Destroy a model from the database
     * @param id the id of the entity we want to destroy (or archive)
     */
    public void destroy(int id) throws EntityNotFoundException, UndeletableException {
        modelDao.destroy(modelDao.find(id));
    }

    /**
     * Update a model on the database
     * @param id the id of the model we want to update
     * @param input the model we want to
     * @return the Api representation of the updated model
     */
    public E update(int id,E input) throws DependantEntityNotFound, EntityNotFoundException {
        input.setId(id);
        return mapper.convertToApiModel(modelDao.save(mapper.convertToModel(input)));
    }


}
