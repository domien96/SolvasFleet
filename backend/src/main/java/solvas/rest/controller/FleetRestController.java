package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.service.models.Fleet;
import solvas.rest.api.models.ApiFleet;
import solvas.rest.query.FleetFilter;
import solvas.service.FleetService;

import javax.validation.Valid;

/**
 * Rest controller for Fleet
 * Visit @ /fleets
 * @author Niko Strijbol
 */
@RestController
public class FleetRestController extends AbstractRestController<Fleet, ApiFleet> {

    /**
     * Default constructor.
     *
     * @param service service class for fleets
     */
    @Autowired
    public FleetRestController(FleetService service) {
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
    @RequestMapping(value = "/fleets", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, FleetFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }

    @Override
    @RequestMapping(value = "/fleets/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/fleets", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody ApiFleet input, BindingResult result) {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/fleets/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> archiveById(@PathVariable int id) {
        return super.archiveById(id);
    }

    @Override
    @RequestMapping(value = "/fleets/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @Valid @RequestBody ApiFleet input,BindingResult result) {
        return super.put(id, input,result);
    }

    @RequestMapping(value = "/companies/{id}/fleets", method = RequestMethod.GET)
    public ResponseEntity<?> listAllByCompany(Pageable pagination, FleetFilter filter,
                                              BindingResult result, @PathVariable int id) {
        filter.setCompany(id);
        return super.listAll(pagination, filter, result);
    }
}