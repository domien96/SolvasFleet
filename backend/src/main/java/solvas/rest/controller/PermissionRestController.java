package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiPermission;
import solvas.rest.query.PermissionFilter;
import solvas.rest.utils.JsonListWrapper;
import solvas.service.PermissionService;
import solvas.service.models.Permission;

/**
 * Rest controller for permissions
 */
@RestController
public class PermissionRestController extends AbstractRestController<Permission, ApiPermission> {
    private PermissionService permissionService;


    /**
     * Default constructor.
     *
     * @param service service class for permissions
     */
    @Autowired
    public PermissionRestController(PermissionService service) {
        super(service);
        this.permissionService = service;
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @return The result.
     */
    @RequestMapping(value = "/auth/permissions", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(0, 'permission', 'READ')")
    public JsonListWrapper<?> listAll() {
        return JsonListWrapper.withTotal(service.findAll());
    }

    /**
     * List permissions for a role
     *
     * @param pagination       Pagination object from request
     * @param permissionFilter Filters from request
     * @param filterResult     Bindingresult
     * @param roleId           roleId taken from request path
     * @return ResponseEntity to return to user
     */
    @PreAuthorize("hasPermission(#roleId, 'role', 'LIST_PERMISSIONS')")
    @RequestMapping(value = "/auth/roles/{roleId}/permissions", method = RequestMethod.GET)
    public ResponseEntity<?> listForRole(
            Pageable pagination,
            PermissionFilter permissionFilter,
            BindingResult filterResult,
            @PathVariable int roleId) {

        try {
            permissionService.filterOnRole(permissionFilter, roleId);
        } catch (EntityNotFoundException e) {
            notFound();
        }
        // If there are errors in the filtering, send bad request.
        return super.listAll(pagination, permissionFilter, filterResult);
    }
}
