package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.models.User;
import solvas.models.validators.UserValidator;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.UserAbstractMapper;
import solvas.rest.api.models.ApiUser;
import solvas.rest.query.PaginationFilter;
import solvas.rest.query.UserFilter;

/**
 * Rest controller for User
 * Visit @ /users
 */
@RestController
public class UserRestController extends AbstractRestController<User,ApiUser> {

    /**
     * Rest controller for User
     *
     * @param daoContext Autowired
     * @param mapper The mapper class for users
     * @param validator Validator for users
     */
    @Autowired
    public UserRestController(DaoContext daoContext, UserAbstractMapper mapper, UserValidator validator) {
        super(daoContext.getUserDao(),mapper,validator);
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
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(PaginationFilter pagination, UserFilter filter) {
        return super.listAll(pagination, filter);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody ApiUser input,BindingResult result) {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody ApiUser input,BindingResult result) {
        return super.put(id, input,result);
    }
}