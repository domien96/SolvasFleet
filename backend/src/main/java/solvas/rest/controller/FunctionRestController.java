package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiFunction;
import solvas.rest.query.FunctionFilter;
import solvas.service.AbstractService;
import solvas.service.FunctionService;
import solvas.service.exceptions.UnarchivableException;
import solvas.service.models.Function;

import javax.validation.Valid;

/**
 * Rest controller for functions
 */
@RestController
public class FunctionRestController extends AbstractRestController<Function, ApiFunction> {

    /**
     * Default constructor.
     *
     * @param service service class for entities
     */
    @Autowired
    protected FunctionRestController(FunctionService service) {
        super(service);
    }

    /**
     *
     * @param pagination The pagination object
     * @param filter Filter the results by this filter
     * @param result BindingResult containing
     * @param userId The user id taken from the request path
     * @return ResponseEntity containing the (paginated) functions
     */
    @RequestMapping(value = "/users/{userId}/functions", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#userId, 'user', 'READ_ROLES')")
    public ResponseEntity<?> listAll(
            Pageable pagination,
            FunctionFilter filter,
            BindingResult result,
            @PathVariable  int userId) {
        filter.setUser(userId);
        return super.listAll(pagination, filter, result);
    }

    /**
     *
     * @param input The ApiFunction to create
     * @param result The bindingresult
     * @param userId the id of the user this permission is for
     * @return ResponseEntity
     */
    @PreAuthorize("hasPermission(#input, 'CREATE')")
    @RequestMapping(value = "/users/{userId}/functions", method = RequestMethod.POST)
    public ResponseEntity<?> post(
            @Valid @RequestBody ApiFunction input,
            BindingResult result,
            @PathVariable  int userId) {
        input.setUser(userId);
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/users/{userId}/functions/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'function', 'DELETE')")
    public ResponseEntity<?> archiveById(@PathVariable int id) throws EntityNotFoundException, UnarchivableException {
        return super.archiveById(id);
    }
}
