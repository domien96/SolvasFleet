package solvas.rest.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import solvas.models.Model;
import solvas.persistence.Dao;
import solvas.persistence.EntityNotFoundException;
import solvas.rest.utils.JsonListWrapper;

/**
 * Abstract REST controller.
 *
 * @param <T> Type of the entity to work with.
 */
public abstract class AbstractRestController<T extends Model> {

    protected final Dao<T> dao;
    protected final Validator validator;

    /**
     * Default constructor.
     *
     * @param dao The dao to work with.
     * @param validator The validator to use when creating/updating entities
     */
    protected AbstractRestController(Dao<T> dao, Validator validator) {
        this.dao = dao;
        this.validator = validator;
    }

    /**
     * Lists all models, and wrap them in JSON object with key
     *
     * @return ResponseEntity
     */
    protected ResponseEntity<?> listAll(String key) {
        return new ResponseEntity<>(new JsonListWrapper<T>(dao.findAll(), key), HttpStatus.OK);
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
            return new ResponseEntity<>(dao.find(id), HttpStatus.OK);
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
     * @param binding The binding to use to validate
     * @return Response with the saved model, or 400.
     */
    protected ResponseEntity<?> post(T input, BindingResult binding) {
        return save(input, binding, () -> dao.create(input));
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
     * @param binding The binding to use to validate
     * @return ResponseEntity
     */
    protected ResponseEntity<?> put(T input, BindingResult binding) {
        return save(input, binding, () -> dao.update(input));
    }

    /**
     * @return ResponseEntity of a 404
     */
    private ResponseEntity<?> notFound() {
        return new ResponseEntity<>("Could not find object.", HttpStatus.NOT_FOUND);
    }

    /**
     * @param input The input entity to save
     * @param binding BindingResult to use for validations
     * @param saveMethod The saveMethod (example: dao.update or dao.create)
     * @return ResponseEntity to return to user
     */
    private ResponseEntity<?> save(T input, BindingResult binding, SaveMethod<T> saveMethod) {
        validator.validate(input, binding);
        if (!binding.hasErrors()) {
            try {
                return new ResponseEntity<>(saveMethod.run(), HttpStatus.OK);
            } catch (EntityNotFoundException unused) {
                return notFound();
            }
        } else {
            return new ResponseEntity<Object>(binding.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Specify how to save an entity
     * @param <T> Type of the entity to save
     */
    protected interface SaveMethod<T> {
        /**
         * Method to run to save an entity
         * @return the saved entity
         */
        T run();
    }
}