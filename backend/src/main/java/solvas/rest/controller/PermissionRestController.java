package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiPermission;
import solvas.rest.api.models.ApiRole;
import solvas.rest.query.PermissionFilter;
import solvas.rest.utils.JsonListWrapper;
import solvas.rest.utils.PagedResult;
import solvas.service.AbstractService;
import solvas.service.PermissionService;
import solvas.service.models.Permission;
import solvas.service.models.Role;

import java.util.Collection;

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
     * @param pagination The pagination information.
     * @param filter The filters.
     * @param result The validation results of the filterResult
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/auth/permissions", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(0, 'permission', 'READ')")
    public ResponseEntity<?> listAll(Pageable pagination, PermissionFilter filter, BindingResult result) {

        // If there are errors in the filtering, send bad request.
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>( new PagedResult<>(service.findAll(pagination, filter).map(ApiPermission::getScope)), HttpStatus.OK);
    }

    /**
     * List permissions for a role
     * @param pagination Pagination object from request
     * @param permissionFilter Filters from request
     * @param filterResult Bindingresult
     * @param roleId roleId taken from request path
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
