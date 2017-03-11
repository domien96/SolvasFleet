package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Role;
import solvas.persistence.role.RoleDao;
import solvas.rest.query.PaginationFilter;
import solvas.rest.query.RoleFilter;


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

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(PaginationFilter pagination, RoleFilter filter) {
        return super.listAll(pagination, filter);
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody Role input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody Role input) {
        return super.put(input);
    }
}
