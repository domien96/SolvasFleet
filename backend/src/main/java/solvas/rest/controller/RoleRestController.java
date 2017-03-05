package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Role;
import solvas.persistence.Role.RoleDao;


/**
 * Rest controller for Role
 * Visit @ /roles
 */
@RestController
public class RoleRestController extends AbstractRestController<Role> {

    /**
     * Rest controller for Role
     *
     * @param dao Autowired
     */
    @Autowired
    public RoleRestController(RoleDao dao) {
        super(dao);
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    ResponseEntity<?> listAll() {
        return super.listAll();
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody Role input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody Role input) {
        return super.put(input);
    }
}
