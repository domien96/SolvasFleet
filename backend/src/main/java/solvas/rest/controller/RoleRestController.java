package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Role;
import solvas.persistence.Role.RoleDao;


/**
 * Visit @ http://localhost:8080/roles
 */
@RestController
public class RoleRestController extends AbstractRestController<Role> {


    /**
     * Rest controller for Role
     * @param dao Autowired
     */
    @Autowired
    public RoleRestController(RoleDao dao) {
        super(dao);
    }

    @Override
    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    ResponseEntity<?> get() {
        return super.get();
    }

    @Override
    @RequestMapping(value = "/roles/{stringId}",method = RequestMethod.GET)
    ResponseEntity<?> getId(@PathVariable String stringId) {
        return super.getId(stringId);
    }

    @Override
    @RequestMapping(value = "/roles",method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody Role input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/roles/{stringId}",method = RequestMethod.DELETE)
    ResponseEntity<?> deleteId(@RequestBody String stringId) {
        return super.deleteId(stringId);
    }

    @Override
    @RequestMapping(value = "/roles",method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody Role input) {
        return super.put(input);
    }
}
