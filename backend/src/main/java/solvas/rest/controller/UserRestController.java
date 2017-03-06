package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.User;
import solvas.persistence.user.UserDao;

/**
 * Rest controller for User
 * Visit @ /users
 */
@RestController
public class UserRestController extends AbstractModelRestController<User> {

    /**
     * Rest controller for User
     *
     * @param dao Autowired
     */
    @Autowired
    public UserRestController(UserDao dao) {
        super(dao);
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    ResponseEntity<?> listAll() {
        return super.listAll();
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody User input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody User input) {
        return super.put(input);
    }
}