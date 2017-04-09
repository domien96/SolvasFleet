package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import solvas.rest.api.models.ApiPermission;
import solvas.service.AbstractService;
import solvas.service.models.Permission;

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
}
