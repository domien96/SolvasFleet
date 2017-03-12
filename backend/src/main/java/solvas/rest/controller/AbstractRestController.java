package solvas.rest.controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import solvas.models.Model;
import solvas.persistence.Dao;
import solvas.persistence.EntityNotFoundException;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.query.Filterable;
import solvas.rest.query.Pageable;
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
    protected AbstractMapper<T,E> mapper;
    protected final Validator validator;

    /**
     * Default constructor.
     *
     * @param dao The dao to work with.
     * @param mapper The mapper class for objects of domain model class T and API-model class E.
     * @param validator The validator to use when creating/updating entities
     */
    protected AbstractRestController(Dao<T> dao, AbstractMapper<T,E> mapper, Validator validator) {
        this.dao = dao;
        this.mapper = mapper;
        this.validator = validator;
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination The pagination information.
     * @param filterable The filters.
     *
     * @return ResponseEntity
     */
    protected ResponseEntity<?> listAll(Pageable pagination, Filterable<T> filterable) {
        Collection<E> collection = new HashSet<>();
        for (T item: dao.findAll(pagination, filterable)){
            collection.add(mapper.convertToApiModel(item));
        }
        JsonListWrapper<E> wrapper = new JsonListWrapper<>(collection);
        wrapper.put("limit", pagination.getLimit());
        wrapper.put("offset", pagination.getLimit() * pagination.getPage());
        wrapper.put("total", dao.count(filterable));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    /**
     * Show the model with the given ID.
     *
     * @param id The ID of the model.
     * @return Response with the model or 404.
     */

    protected ResponseEntity<?> getById(int id) {
        try {
            return new ResponseEntity<>(mapper.convertToApiModel(dao.find(id)), HttpStatus.OK);
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
    protected ResponseEntity<?> post(E input, BindingResult binding) {
            return save(input,binding,() -> {
                T model = mapper.convertToModel(input);
                return mapper.convertToApiModel(dao.create(model));
            });
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
    protected ResponseEntity<?> put(int id,E input,BindingResult binding) {
        return save(input, binding, () -> {
            T model = mapper.convertToModel(input);
            model.setId(id);
            return mapper.convertToApiModel(dao
                    .save(model));
        });
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
    private ResponseEntity<?> save(E input, BindingResult binding, SaveMethod<E> saveMethod) {
        validator.validate(input, binding);
        if (!binding.hasErrors()) {
            try {
                return new ResponseEntity<>(saveMethod.run(), HttpStatus.OK);
            } catch (EntityNotFoundException unused) {
                // Notify user the record wasn't found
                // Shouldn't happen when creating a record
                return notFound();
            }
        } else {
            // Return validation errors to user
            return new ResponseEntity<Object>(
                    new JsonListWrapper<>(binding.getFieldErrors(), "errors"),
                    HttpStatus.BAD_REQUEST);
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