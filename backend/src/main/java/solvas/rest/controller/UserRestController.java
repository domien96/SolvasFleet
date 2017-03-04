package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.User;
import solvas.persistence.user.UserDao;

/**
 *  Rest controller for User
 */
@RestController
public class UserRestController extends AbstractRestController<User> {

    /**
     * Rest controller for User
     * @param dao Autowired
     */
    @Autowired
    public UserRestController(UserDao dao) {
        super(dao);
    }

    @Override
    @RequestMapping(value = "/users",method = RequestMethod.GET)
    ResponseEntity<?> get() {
        return super.get();
    }

    @Override
    @RequestMapping(value = "/users/{stringId}",method = RequestMethod.GET)
    ResponseEntity<?> getId(@PathVariable String stringId) {
        return super.getId(stringId);
    }

    @Override
    @RequestMapping(value = "/users",method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody User input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/users/{stringId}",method = RequestMethod.DELETE)
    ResponseEntity<?> deleteId(@RequestBody String stringId) {
        return super.deleteId(stringId);
    }

    @Override
    @RequestMapping(value = "/users",method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody User input) {
        return super.put(input);
    }
}