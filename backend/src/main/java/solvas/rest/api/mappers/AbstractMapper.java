package solvas.rest.api.mappers;

import solvas.models.Model;
import solvas.persistence.api.DaoContext;

/**
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
     * TODO document
     * @param api
     * @return
     */
    public abstract T convertToModel(E api);

    /**
     * TODO document
     * @param model
     * @return
     */
    public abstract E convertToApiModel(T model);


}
