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
public class UserRestController extends AbstractRestController<User> {

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
    public ResponseEntity<?> listAll() {
        return super.listAll("users");
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody User input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody User input) {
        return super.put(id, input);
    }
}