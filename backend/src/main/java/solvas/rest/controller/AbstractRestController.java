package solvas.rest.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import solvas.persistence.Dao;
import solvas.persistence.EntityNotFoundException;

/**
 * Abstract REST controller.
 *
 * @param <T> Type of the entity to work with.
 */
public abstract class AbstractRestController<T> {

    protected final Dao<T> dao;

    /**
     * Default constructor.
     *
     * @param dao The dao to work with.
     */
    public AbstractRestController(Dao<T> dao) {
        this.dao = dao;
    }

    /**
     * Lists all models.
     *
     * @return ResponseEntity
     */
    ResponseEntity<?> listAll() {
        return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
    }

    /**
     * Show the model with the given ID.
     *
     * @param id The ID of the model.
     *
     * @return Response with the model or 404.
     */
    ResponseEntity<?> getById(int id) {
        try {
            return new ResponseEntity<>(dao.find(id), HttpStatus.OK);

        } catch (EntityNotFoundException unused) {
            return new ResponseEntity<>("Object with id not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Handle cases where the path variable cannot be converted to the required type.
     *
     * @param ex The exception.
     *
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
     *
     * @return Response with the saved model, or 400.
     */
    ResponseEntity<?> post(T input) {
        //post message met application/json {"name":"comp4","vat":"4"}
        //TODO validate whether input is valid

        if (input != null) {
            T result = dao.save(input);
            return new ResponseEntity<>(result, HttpStatus.OK); // add URI to location header field
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes a model from db
     * 
     * Untested
     *
     * @param id id of the model
     *
     * @return ResponseEntity
     */
    ResponseEntity<?> deleteById(int id) {
        try {
            dao.destroy(dao.find(id));
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (EntityNotFoundException unused) {
            return new ResponseEntity<>("Object with id not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates a model in db TODO
     *
     * @param input model to be updated
     *
     * @return ResponseEntity
     */
    ResponseEntity<?> put(T input) {
        //TODO
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}