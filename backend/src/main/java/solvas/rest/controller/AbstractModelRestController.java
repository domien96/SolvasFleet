package solvas.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import solvas.models.Model;
import solvas.persistence.Dao;

/**
 * Abstract Model REST controller.
 * @param <T> Type of the entity to work with.
 */
public abstract class AbstractModelRestController<T extends Model> extends AbstractRestController<T> {
    /**
     * Default constructor.
     *
     * @param dao The dao to work with.
     */
    public AbstractModelRestController(Dao<T> dao) {
        super(dao);
    }

    /**
     * Set created at and updated at by querying the db again
     * @param input The model to save.
     *
     * @return Response with the saved model, or 400.
     */
    @Override
    ResponseEntity<?> post(T input) {
        //post message met application/json {"name":"comp4","vat":"4"}
        //TODO validate whether input is valid

        if (input != null) {
            T result = dao.save(input);
            result = dao.find(result.getId()); //Set created at and updated at correct
            return new ResponseEntity<>(result, HttpStatus.OK); // add URI to location header field
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
