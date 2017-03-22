package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.models.Fleet;
import solvas.models.validators.FleetValidator;
import solvas.rest.api.models.ApiFleet;
import solvas.rest.query.FleetFilter;
import solvas.rest.query.PaginationFilter;
import solvas.rest.service.FleetService;

/**
 * @author Niko Strijbol
 */
@RestController
public class FleetRestController extends AbstractRestController<Fleet, ApiFleet> {

    /**
     * Default constructor.
     *
     * @param service service class for fleets
     * @param validator The validator to use when creating/updating entities
     */
    @Autowired
    public FleetRestController(FleetService service,FleetValidator validator) {
        super(validator,service);
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination The pagination information.
     * @param paginationResult The validation results of the pagination object.
     * @param filter The filters.
     * @param result The validation results of the filterResult
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/fleets", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(PaginationFilter pagination, BindingResult paginationResult, FleetFilter filter, BindingResult result) {
        return super.listAll(pagination, paginationResult, filter, result);
    }

    @Override
    @RequestMapping(value = "/fleets/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/fleets", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody ApiFleet input, BindingResult result) {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/fleets/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/fleets/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody ApiFleet input,BindingResult result) {
        return super.put(id, input,result);
    }
}