package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.rest.api.models.ApiFunction;
import solvas.rest.query.FunctionFilter;
import solvas.service.AbstractService;
import solvas.service.models.Function;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import javax.validation.Validator;


@RestController
public class FunctionRestController extends AbstractRestController<Function, ApiFunction> {

    private final Validator validator;
    /**
     * Default constructor.
     *
     * @param service service class for entities
     */
    @Autowired
    protected FunctionRestController(AbstractService<Function, ApiFunction> service, Validator validator) {
        super(service);
        this.validator = validator;
    }

    @RequestMapping(value = "/users/{userId}/functions", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(
            Pageable pagination,
            FunctionFilter filter,
            BindingResult result,
            @PathVariable  int userId) {
        filter.setUser(userId);
        return super.listAll(pagination, filter, result);
    }

    @RequestMapping(value = "/users/{userId}/functions", method = RequestMethod.POST)
    public ResponseEntity<?> post(
            @RequestBody ApiFunction input,
            BindingResult result,
            @PathVariable  int userId) {
        input.setUser(userId);
        if(validator.validate(input).size() > 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/users/{userId}/functions/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> archiveById(@PathVariable int id) {
        return super.archiveById(id);
    }
}
