package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.service.models.User;
import solvas.rest.api.models.ApiUser;
import solvas.rest.query.UserFilter;
import solvas.service.UserService;

import javax.validation.Valid;

/**
 * Rest controller for User
 * Visit @ /users
 */
@RestController
public class UserRestController extends AbstractRestController<User,ApiUser> {

    /**
     * Rest controller for User
     *
     * @param service service class for users
     */
    @Autowired
    public UserRestController(UserService service) {
        super( service);
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
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(0, 'user', 'READ')")
    public ResponseEntity<?> listAll(Pageable pagination, UserFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }

    @Override
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#userId, 'user', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int userId) {
        return super.getById(userId);
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(0, 'user', 'WRITE')")
    public ResponseEntity<?> post(@RequestBody @Valid ApiUser input, BindingResult result) {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#userId, 'user', 'WRITE')")
    public ResponseEntity<?> archiveById(@PathVariable int userId) {
        return super.archiveById(userId);
    }

    @Override
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#userId, 'user', 'WRITE')")
    public ResponseEntity<?> put(@PathVariable int userId, @Valid @RequestBody ApiUser input, BindingResult result) {
        return super.put(userId, input,result);
    }
}