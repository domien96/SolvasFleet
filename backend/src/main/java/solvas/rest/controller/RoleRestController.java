package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.rest.api.models.ApiRole;
import solvas.rest.query.RoleFilter;
import solvas.service.RoleService;
import solvas.service.models.Role;

import javax.validation.Valid;
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
    @PreAuthorize("hasPermission(0, 'role', 'READ')")
    public ResponseEntity<?> listAll(Pageable pagination, RoleFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }

    @Override
    @RequestMapping(value = "/auth/roles/{roleId}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#roleId, 'role', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int roleId) {
        return super.getById(roleId);
    }

    @Override
    @RequestMapping(value = "/auth/roles", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#input, 'CREATE')")
    public ResponseEntity<?> post(@Valid @RequestBody ApiRole input, BindingResult result) {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/auth/roles/{roleId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#roleId, 'role', 'DELETE')")
    public ResponseEntity<?> archiveById(@PathVariable int roleId) {
        return super.archiveById(roleId);
    }

    @Override
    @RequestMapping(value = "/auth/roles/{roleId}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#input, 'EDIT')")
    public ResponseEntity<?> put(@PathVariable int roleId, @RequestBody ApiRole input,BindingResult result) {
        return super.put(roleId, input,result);
    }

    /**
     *
     * @param roleId Id of the role to update permissions for
     * @param permissions List of permissions (ids)
     * @param result Validation result
     * @return ReponseEntity
     */
    @RequestMapping(value = "/auth/roles/{roleId}/permissions", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#roleId, 'role', 'WRITE')")
    public ResponseEntity<?> putPermissions(@PathVariable int roleId,
                                            @RequestBody Set<String> permissions,
                                            BindingResult result) {
        ApiRole r = new ApiRole();
        r.setPermissions(permissions);
        return super.put(roleId, r, result);
    }
}
