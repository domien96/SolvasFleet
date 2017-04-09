package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import solvas.rest.api.models.ApiPermission;
import solvas.rest.query.PermissionFilter;
import solvas.service.AbstractService;
import solvas.service.models.Permission;
import solvas.service.models.Role;

/**
 * Rest controller for permissions
 */
@RestController
public class PermissionRestController extends AbstractRestController<Permission, ApiPermission> {
    /**
     * Default constructor.
     *
     * @param service service class for permissions
     */
    @Autowired
    public PermissionRestController(AbstractService<Permission, ApiPermission> service) {
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
    @RequestMapping(value = "/auth/permissions", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, PermissionFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }


    /**
     * List permissions for a role
     * @param pagination Pagination object from request
     * @param filter Filters from request
     * @param result Bindingresult
     * @param roleId roleId taken from request path
     * @return ResponseEntity to return to user
     */
    @RequestMapping(value = "/auth/roles/{roleId}/permissions", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(
            Pageable pagination,
            PermissionFilter filter,
            BindingResult result,
            int roleId) {
        filter.setRole(roleId);

        return super.listAll(pagination, filter, result);
    }
}
