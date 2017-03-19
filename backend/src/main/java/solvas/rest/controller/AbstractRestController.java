package solvas.rest.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ExceptionHandler;
import solvas.models.Model;
import solvas.persistence.api.Dao;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.Filter;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.DependantEntityNotFound;
import solvas.rest.api.models.ApiModel;
import solvas.rest.query.Pageable;
import solvas.rest.utils.JsonListWrapper;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Abstract REST controller.
 *
 * @param <T> Type of the entity to work with.
 * @param <E> The type of the API model
 */
@Component
@Transactional // TODO Replace by services
public abstract class AbstractRestController<T extends Model, E extends ApiModel> {

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
     * @param paginationResult The validation results of the pagination object.
     * @param filter The filters.
     * @param filterResult The validation results of the filterResult
     *
     * @return ResponseEntity
     */
    protected ResponseEntity<?> listAll(Pageable pagination, BindingResult paginationResult, Filter<T> filter, BindingResult filterResult) {

        // If there are errors in the filtering, send bad request.
        if (filterResult.hasErrors() || paginationResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Collection<E> collection = new HashSet<>();
        for (T item: dao.findAll(pagination, filter)){
            collection.add(mapper.convertToApiModel(item));
        }
        ArrayList<E> sortedList = new ArrayList<>(collection);
        sortedList.sort(Comparator.comparingInt(Model::getId));
        JsonListWrapper<E> wrapper = new JsonListWrapper<>(collection);
        wrapper.put("limit", pagination.getLimit());
        wrapper.put("offset", pagination.getLimit() * pagination.getPage());
        wrapper.put("total", dao.count(filter));
        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    /**
     * Show the model with the given ID.
     *
     * @param id The ID of the model.
     * @return Response with the model or 404.
     */

    protected ResponseEntity<?> getById(int id) {
        return new ResponseEntity<>(mapper.convertToApiModel(dao.find(id)), HttpStatus.OK);
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
     *
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
     *
     * TODO: see if this could be done by validation.
     *
     * @param e The exception.
     *
     * @return 422 with the field.
     */
    @ExceptionHandler(DependantEntityNotFound.class)
    public ResponseEntity<?> handleDependantNotFound(DependantEntityNotFound e) {
        JsonListWrapper<String> wrapper = new JsonListWrapper<>(
                Collections.singleton(e.getField()),
                JsonListWrapper.ERROR_KEY
        );

        return new ResponseEntity<>(wrapper, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handle cases where there are JSON syntax errors.
     *
     * @param ex The exception.
     *
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
    private ResponseEntity<?> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Save a new model in the database.
     *
     * @param input The model to save.
     * @param binding The binding to use to validate
     * @return Response with the saved model, or 400.
     */
    protected ResponseEntity<?> post(E input, BindingResult binding) {
        return save(input, binding, () -> {
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
        dao.destroy(dao.find(id));
        return new ResponseEntity<>(HttpStatus.OK);
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
            input.setId(id);
            T model = mapper.convertToModel(input);

            return mapper.convertToApiModel(dao
                    .update(model));
        });
    }

    /**
     * @param input The input entity to save
     * @param binding BindingResult to use for validations
     * @param saveMethod The saveMethod (example: dao.update or dao.create)
     * @return ResponseEntity to return to user
     */
    private ResponseEntity<?> save(E input, BindingResult binding, SaveMethod<E> saveMethod) {
        if (validator != null) {
            validator.validate(input, binding);
        }
        if (!binding.hasErrors()) {
            return new ResponseEntity<>(saveMethod.run(), HttpStatus.OK);
        } else {
            // Return validation errors to user
            return new ResponseEntity<Object>(
                    new JsonListWrapper<>(
                            binding.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList()),
                            JsonListWrapper.ERROR_KEY
                    ),
                    HttpStatus.BAD_REQUEST
            );
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