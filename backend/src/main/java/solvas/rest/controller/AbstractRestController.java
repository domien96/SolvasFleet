package solvas.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import solvas.persistence.Dao;

/**
 * Abstract Rest controller to minimize code duplication
 * @param <T> abstract dao
 */
public abstract class AbstractRestController<T> {

    protected Dao<T> dao;

    /**
     * Abstract Rest controller to minimize code duplication
     * @param dao abstract
     */
    public AbstractRestController(Dao<T> dao) {
        this.dao = dao;
    }


    ResponseEntity<?> get(){
        return new ResponseEntity<>(dao.findAll(), HttpStatus.OK);
    }


    ResponseEntity<?> getId(String stringId){
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

    ResponseEntity<?> post(T input) {
        //post message met aplication/json {"name":"comp4","vat":"4"}
        //TODO validate wether inpu is valid

        if (input!=null){
            dao.save(input);
            return new ResponseEntity<>(HttpStatus.OK); // add URI to location header field
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    ResponseEntity<?> deleteId(String stringId){
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    ResponseEntity<?> put( T input){
        //TODO
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }





}
