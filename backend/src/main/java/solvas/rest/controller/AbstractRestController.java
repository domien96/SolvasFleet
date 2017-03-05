package solvas.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import solvas.persistence.Dao;

/**
 * Abstract Rest controller to minimize code duplication
 * @param <T> abstract dao
 */
public abstract class AbstractRestController<T> {

    protected final Dao<T> dao;

    /**
     * Abstract Rest controller to minimize code duplication
     * @param dao abstract
     */
    public AbstractRestController(Dao<T> dao) {
        this.dao = dao;
    }

    /**
     * lists all models of type of the dao
     * @return ResponseEntity
     */
    ResponseEntity<?> listAll(){
        return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
    }

    /**
     * lists model of type of the dao with id
     * @param stringId
     * @return ResponseEntity
     */
    ResponseEntity<?> getById(String stringId){
        int id;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e){
            // TODO: make 404's have proper JSON bodies
            return new ResponseEntity<>("Id not valid", HttpStatus.NOT_FOUND);
        }
        T result = dao.find(id);
        if(result == null) {
            return new ResponseEntity<>("Object with id not found", HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * post a new model in de db of type of the dao
     * @param input model to be put in the db
     * @return ResponseEntity
     */
    ResponseEntity<?> post(T input) {
        //post message met application/json {"name":"comp4","vat":"4"}
        //TODO validate whether input is valid

        if (input!=null){
            dao.save(input);
            return new ResponseEntity<>(HttpStatus.OK); // add URI to location header field
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes a model from db TODO
     * @param stringId id of the model
     * @return ResponseEntity
     */
    ResponseEntity<?> deleteById(String stringId){
        //TODO
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * Updates a model in db TODO
     * @param input model to be updated
     * @return ResponseEntity
     */
    ResponseEntity<?> put( T input){
        //TODO
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }





}
