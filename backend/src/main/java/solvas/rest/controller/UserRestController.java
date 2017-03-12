package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.User;
import solvas.persistence.user.UserDao;
import solvas.rest.api.mappings.UserMapping;
import solvas.rest.api.models.ApiUser;

/**
 * Rest controller for User
 * Visit @ /users
 */
@RestController
public class UserRestController extends AbstractRestController<User,ApiUser> {

    /**
     * Rest controller for User
     *
     * @param dao Autowired
     */
    @Autowired
    public UserRestController(UserDao dao,UserMapping mapping) {
        super(dao,mapping);
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
    public ResponseEntity<?> post(@RequestBody ApiUser input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody ApiUser input) {
        return super.put(input);
    }
}