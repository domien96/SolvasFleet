package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.User;
import solvas.models.Vehicle;
import solvas.persistence.user.UserDao;

import java.util.Collection;


@RestController
public class UserRestController extends AbstractRestController<User> {


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
    @RequestMapping(value = "/users/{s_id}",method = RequestMethod.GET)
    ResponseEntity<?> getId(@PathVariable String s_id) {
        return super.getId(s_id);
    }

    @Override
    @RequestMapping(value = "/users",method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody User input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/users/{s_id}",method = RequestMethod.DELETE)
    ResponseEntity<?> deleteId(@RequestBody String s_id) {
        return super.deleteId(s_id);
    }

    @Override
    @RequestMapping(value = "/users",method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody User input) {
        return super.put(input);
    }
}