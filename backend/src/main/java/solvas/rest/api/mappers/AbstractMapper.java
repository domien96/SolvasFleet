package solvas.rest.api.mappers;

import solvas.models.Model;
import solvas.persistence.api.DaoContext;

/**
 * Mapper between a Model in the persistence layer and it's representation in the REST API
 * Class parameter T : class of the domain model class.
 * Class parameter E : class of the api model class.
 * Created by steve on 11/03/2017.
 */
public abstract class AbstractMapper<T extends Model,E> {

    protected final DaoContext daoContext;

    /**
     * TODO document
     */
    public AbstractMapper(DaoContext daoContext) {
        this.daoContext = daoContext;
    }


    /**
     * Convert an ApiModel to a Model in the persistence layer
     * @param api An model according to the REST api
     * @return A Model in the persistence layer
     */
    public abstract T convertToModel(E api);

    /**
     * Convert a Model in the persistence layer to an ApiModel
     * @param model A Model in the persistence layer
     * @return An model according to the REST api
     */
    public abstract E convertToApiModel(T model);


}
