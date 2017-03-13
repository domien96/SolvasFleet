package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.models.Role;
import solvas.models.validators.RoleValidator;
import solvas.persistence.DaoContext;
import solvas.rest.api.mappers.RoleMapper;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.rest.api.models.ApiRole;
import solvas.rest.query.PaginationFilter;
import solvas.rest.query.RoleFilter;


/**
 * Rest controller for Role
 * Visit @ /roles
 */
@RestController
public class RoleRestController extends AbstractRestController<Role,ApiRole> {

    /**
     * Rest controller for Role
     *
     * @param daoContext Autowired
     * @param mapper The mapper class for roles
     * @param validator Validator for roles
     */
    @Autowired
    public RoleRestController(DaoContext daoContext, RoleMapper mapper, RoleValidator validator) {
        super(daoContext.getRoleDao(),mapper,validator);
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination The pagination information.
     * @param filter The filters.
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(PaginationFilter pagination, RoleFilter filter) throws FieldNotFoundException {
        return super.listAll(pagination, filter);
    }


    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) throws FieldNotFoundException {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody ApiRole input,BindingResult result) throws FieldNotFoundException {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/roles/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody ApiRole input,BindingResult result) throws FieldNotFoundException {
        return super.put(id, input,result);
    }
}
