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

    @RequestMapping(value = "/auth/permissions", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, PermissionFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }


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
