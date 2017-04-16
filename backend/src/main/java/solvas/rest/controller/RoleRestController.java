package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.query.PermissionFilter;
import solvas.service.models.Role;
import solvas.rest.api.models.ApiRole;
import solvas.rest.query.RoleFilter;
import solvas.service.RoleService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;


/**
 * Rest controller for Role
 * Visit @ /roles
 */
@RestController
public class RoleRestController extends AbstractRestController<Role,ApiRole> {

    /**
     * Rest controller for Role
     *
     * @param service service class for roles
     */
    @Autowired
    public RoleRestController(RoleService service) {
        super(service);
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination The pagination information.
     * @param filter The filters.
     * @param result The validation results of the filterResult
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/auth/roles", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, RoleFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }

    @Override
    @RequestMapping(value = "/auth/roles/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/auth/roles", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody ApiRole input, BindingResult result) {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/auth/roles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> archiveById(@PathVariable int id) {
        return super.archiveById(id);
    }

    @Override
    @RequestMapping(value = "/auth/roles/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody ApiRole input,BindingResult result) {
        return super.put(id, input,result);
    }

    /**
     *
     * @param id Id of the role to update permissions for
     * @param permissions List of permissions (ids)
     * @param result Validation result
     * @return ReponseEntity
     */
    @RequestMapping(value = "/auth/roles/{id}/permissions", method = RequestMethod.PUT)
    public ResponseEntity<?> putPermissions(@PathVariable int id,
                                            @RequestBody Set<Integer> permissions,
                                            BindingResult result) {
        ApiRole r = new ApiRole();
        r.setPermissions(permissions);
        return super.put(id, r, result);
    }
}
