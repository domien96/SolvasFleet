package solvas.rest.api.mappers;

import org.springframework.beans.factory.annotation.Value;
import solvas.models.Model;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Mapper between a Model in the domain layer and it's representation in the REST API
 * This class is needed because the API isn't a perfect match with our domain layer
 * @param <T> class of the domain model class.
 * @param <E> class of the api model class.
 * Created by steve on 11/03/2017.
 */
public abstract class AbstractMapper<T extends Model,E> {

    /**
     * Contains protocol, authentication and domain
     */
    protected final String urlroot;

    protected final DaoContext daoContext;

    /**
     * @param daoContext DaoContext 
     */
    public AbstractMapper(DaoContext daoContext) throws UnknownHostException {
        this.daoContext = daoContext;
         urlroot = InetAddress.getLocalHost().getHostName(); // TODO FIX
    }

    /**
     * @param daoContext DaoContext
     * @param urlroot The root of the url. e.g. http://localhost:8080/
     */
    public AbstractMapper(DaoContext daoContext, String urlroot) {
        this.daoContext = daoContext;
        this.urlroot = urlroot;
    }


    /**
     * Convert an ApiModel to a Model in the persistence layer
     * @param api An model according to the REST api
     * @return A Model in the persistence layer
     */
    public abstract T convertToModel(E api) throws DependantEntityNotFound, EntityNotFoundException;

    /**
     * Convert a Model in the persistence layer to an ApiModel
     * @param model A Model in the persistence layer
     * @return An model according to the REST api
     */
    public abstract E convertToApiModel(T model);


}
