package solvas.rest.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.Filter;
import solvas.rest.api.models.ApiModel;
import solvas.rest.api.models.errors.ApiError;
import solvas.rest.api.models.errors.ErrorType;
import solvas.rest.utils.JsonListWrapper;
import solvas.rest.utils.PagedResult;
import solvas.service.AbstractService;
import solvas.service.exceptions.UndeletableException;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Model;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Abstract REST controller. Controllers handle incoming requests.
 *
 * @param <T> Type of the entity to work with.
 * @param <E> The type of the API model
 */
@Component // TODO Replace by services
public abstract class AbstractRestController<T extends Model, E extends ApiModel> {

    protected AbstractService<T,E> service;

    /**
     * Default constructor.
     *
     * @param service service class for entities
     */
    protected AbstractRestController(AbstractService<T,E> service) {
        this.service=service;
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination       The pagination information.
     * @param filter           The filters.
     * @param filterResult     The validation results of the filterResult
     * @return ResponseEntity
     */
    protected ResponseEntity<?> listAll(Pageable pagination, Filter<T> filter, BindingResult filterResult) {

        // If there are errors in the filtering, send bad request.
        if (filterResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PagedResult<E> pagedResult = new PagedResult<>(service.findAll(pagination, filter));
        return new ResponseEntity<>(pagedResult, HttpStatus.OK);
    }

    /**
     * Show the model with the given ID.
     *
     * @param id The ID of the model.
     * @return Response with the model or 404.
     */

    protected ResponseEntity<?> getById(int id) {
        try {
            return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return notFound();
        }
    }

    /**
     * Handle cases where the path variable cannot be converted to the required type.
     *
     * @param ex The exception.
     * @return A 400 error.
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatchException(TypeMismatchException ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle cases where the input json cannot be mapped. This means the JSON syntax should
     * be valid, but our mapping does not match. This often means a certain key does not contain
     * the right type.
     *
     * @param ex The exception.
     * @return A 422 error with the fields that caused an error.
     */
    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<?> handleJsonMappingException(JsonMappingException ex) {
        JsonListWrapper<String> wrapper = new JsonListWrapper<>(
                ex.getPath().stream().map(JsonMappingException.Reference::getFieldName).collect(Collectors.toList()),
                JsonListWrapper.ERROR_KEY
        );
        return new ResponseEntity<>(wrapper, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handle cases where a dependant entity was not found, e.g. a vehicle with a non-existent
     * fleet.
     * <p>
     * TODO: see if this could be done by validation.
     *
     * @param e The exception.
     * @return 422 with the field.
     */
    @ExceptionHandler(DependantEntityNotFound.class)
    public ResponseEntity<?> handleDependantNotFound(DependantEntityNotFound e) {

        ApiError error = new ApiError(ErrorType.INVALID, e.getField(), e.getEntityMessage());

        JsonListWrapper<ApiError> wrapper = new JsonListWrapper<>(
                Collections.singleton(error),
                JsonListWrapper.ERROR_KEY
        );

        return new ResponseEntity<>(wrapper, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UndeletableException.class)
    public ResponseEntity<?> handleUndeletableException(UndeletableException e) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    /**
     * Handle cases where there are JSON syntax errors.
     *
     * @param ex The exception.
     * @return A 400 error.
     */
    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<?> handleJsonParseException(JsonParseException ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * @return ResponseEntity of a 404
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<?> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Save a new model in the database.
     *
     * @param input   The model to save.
     * @param binding The binding to use to validate
     * @return Response with the saved model, or 400.
     * @throws EntityNotFoundException Should never be thrown, because we're creating a new record. If this happens, a bug is found.
     */
    protected ResponseEntity<?> post(E input, BindingResult binding)  {
        try {
            return save(input, binding, () -> service.create(input));
        } catch (DependantEntityNotFound e) {
            return handleDependantNotFound(e);
        } catch (EntityNotFoundException e) {
            return notFound();
        }
    }

    /**
     * Archives a model in the db
     *
     * @param id id of the model
     * @return ResponseEntity
     */
    protected ResponseEntity<?> archiveById(int id) {
        try {
            service.archive(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return notFound();
        }
    }

    /**
     * Updates a model in db
     *
     * @param input   model to be updated
     * @param binding The binding to use to validate
     * @return ResponseEntity
     */
    protected ResponseEntity<?> put(int id, E input, BindingResult binding) {
        try {
            return save(input, binding, () -> service.update(id,input));
        } catch (DependantEntityNotFound e) {
            return handleDependantNotFound(e);
        } catch (EntityNotFoundException e) {
            return notFound();
        }
    }

    /**
     * @param input      The input entity to save
     * @param binding    BindingResult to use for validations
     * @param saveMethod The saveMethod (example: dao.update or dao.create)
     * @return ResponseEntity to return to user
     */
    private ResponseEntity<?> save(E input, BindingResult binding, SaveMethod<E> saveMethod) throws DependantEntityNotFound, EntityNotFoundException {
        if (!binding.hasErrors()) {
            return new ResponseEntity<>(saveMethod.run(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ApiError.from(binding), HttpStatus.BAD_REQUEST);
            // Return validation errors to user
//            return new ResponseEntity<Object>(
//                    new JsonListWrapper<>(
//                            binding.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList()),
//                            JsonListWrapper.ERROR_KEY
//                    ),
//
//                    HttpStatus.BAD_REQUEST
//            );
        }
    }

    /**
     * Specify how to save an entity
     *
     * @param <T> Type of the entity to save
     */
    @FunctionalInterface
    protected interface SaveMethod<T> {
        /**
         * Method to run to save an entity
         *
         * @return the saved entity
         */
        T run() throws DependantEntityNotFound,EntityNotFoundException;
    }
}