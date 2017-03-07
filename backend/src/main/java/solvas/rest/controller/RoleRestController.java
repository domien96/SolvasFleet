package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import solvas.models.Role;
import solvas.models.validators.RoleValidator;
import solvas.persistence.role.RoleDao;


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
    public RoleRestController(RoleDao dao, RoleValidator validator) {
        super(dao, validator);
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        return super.listAll("roles");
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody Role input, BindingResult result) {
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody Role input, BindingResult result) {
        return super.put(input, result);
    }
}
