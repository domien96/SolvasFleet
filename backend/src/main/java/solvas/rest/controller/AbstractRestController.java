package solvas.rest.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import solvas.models.Model;
import solvas.persistence.Dao;
import solvas.persistence.EntityNotFoundException;
import solvas.rest.api.mappings.Mapping;
import solvas.rest.utils.JsonListWrapper;

import java.util.Collection;
import java.util.HashSet;

/**
 * Abstract REST controller.
 *
 * @param <T> Type of the entity to work with.
 */
@Component
@Transactional // Replace by services
public abstract class AbstractRestController<T extends Model, E> {

    protected final Dao<T> dao;
    protected Mapping<T,E> mapping;

    /**
     * Default constructor.
     *
     * @param dao The dao to work with.
     */
    protected AbstractRestController(Dao<T> dao, Mapping<T,E> mapping) {
        this.dao = dao;
        this.mapping = mapping;
    }

    /**
     * Lists all models, and wrap them in JSON object with key
     *
     * @return ResponseEntity
     */
    protected ResponseEntity<?> listAll(String key) {
        Collection<E> collection = new HashSet<>();
        for (T item: dao.findAll()){
            collection.add(mapping.convertToApiModel(item));
        }
        return new ResponseEntity<>(new JsonListWrapper<E>(collection, key), HttpStatus.OK);
    }

    /**
     * List all models.
     *
     * @return ReponseEntity
     */
    public abstract ResponseEntity<?> listAll();

    /**
     * Show the model with the given ID.
     *
     * @param id The ID of the model.
     * @return Response with the model or 404.
     */

    protected ResponseEntity<?> getById(int id) {
        try {
            return new ResponseEntity<>(mapping.convertToApiModel(dao.find(id)), HttpStatus.OK);
        } catch (EntityNotFoundException unused) {
            return notFound();
        }
    }

    /**
     * Handle cases where the path variable cannot be converted to the required type.
     *
     * @param ex The exception.
     * @return The error entity, containing a message explaining the error.
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatchException(TypeMismatchException ex) {

        String actualType = ClassUtils.getDescriptiveType(ex.getValue());
        String requiredType = ex.getRequiredType().getTypeName();
        String message = "Failed to convert %1s to %2s. Input was: %3s.";

        return new ResponseEntity<>(String.format(message, actualType, requiredType, ex.getValue()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Save a new model in the database.
     *
     * @param input The model to save.
     * @return Response with the saved model, or 400.
     */
    protected ResponseEntity<?> post(E input) {
        //post message met application/json {"name":"comp4","vat":"4"}
        //TODO validate whether input is valid

        if (input != null) {
            T result = dao.save(mapping.convertToModel(input));
            return new ResponseEntity<>(mapping.convertToApiModel(result), HttpStatus.OK); // add URI to location header field
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes a model from db
     *
     * @param id id of the model
     * @return ResponseEntity
     */
    protected ResponseEntity<?> deleteById(int id) {
        try {
            dao.destroy(dao.find(id));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException unused) {
            return notFound();
        }
    }

    /**
     * Updates a model in db
     *
     * @param input model to be updated
     * @return ResponseEntity
     */
    protected ResponseEntity<?> put(E input) {
        try {
            return new ResponseEntity<>(mapping.convertToApiModel(dao
                    .save(mapping.convertToModel(input))), HttpStatus.OK);
        } catch (EntityNotFoundException unused) {
            return notFound();
        }
    }

    private ResponseEntity<?> notFound() {
        return new ResponseEntity<>("Could not find object.", HttpStatus.NOT_FOUND);
    }
}