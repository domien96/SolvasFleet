package solvas.rest.api.mappers;

import solvas.models.Model;
import solvas.persistence.api.DaoContext;

/**
 * Mapper between a Model in the domain layer and it's representation in the REST API
 * This class is needed because the API isn't a perfect match with our domain layer
 * @param <T> class of the domain model class.
 * @param <E> class of the api model class.
 * Created by steve on 11/03/2017.
 */
public abstract class AbstractMapper<T extends Model,E> {

    protected final DaoContext daoContext;

    /**
     * @param daoContext DaoContext 
     */
    public AbstractMapper(DaoContext daoContext) {
        this.daoContext = daoContext;
    }


    /**
     * Convert an ApiModel to a Model in the persistence layer
     * @param api A model according to the REST api
     * @param baseModel The base model you want to start with (when working with a model captured from the database)
     * @return A Model in the persistence layer
     */
    public abstract T convertToModel(E api, T baseModel) throws DependantEntityNotFound;

    /**
     * Convert an ApiModel to a Model in the persistence layer
     * @param api A model according to the REST api
     * @return A Model in the persistence layer
     */
    public abstract T convertToEmptyModel(E api) throws DependantEntityNotFound;

    /**
     * Convert a Model in the persistence layer to an ApiModel
     * @param model A Model in the persistence layer
     * @return An model according to the REST api
     */
    public abstract E convertToApiModel(T model);


}
