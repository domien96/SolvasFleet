package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.models.User;
import solvas.models.validators.UserValidator;
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
     * @param validator Validator for users
     */
    @Autowired
    public UserRestController(UserDao dao, UserValidator validator) {
        super(dao, validator);
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
    public ResponseEntity<?> post(@RequestBody User input, BindingResult result) {
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody User input, BindingResult result) {
        return super.put(input, result);
    }
}