package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * @param companyId Id of the company to filter fleets on
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/companies/{companyId}/fleets", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#companyId, 'company', 'READ')")
    public ResponseEntity<?> listAll(Pageable pagination, FleetFilter filter, BindingResult result, @PathVariable int companyId) {
        filter.setCompany(companyId);
        return super.listAll(pagination, filter, result);
    }

    @Override
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#fleetId, 'fleet', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int fleetId) {
        return super.getById(fleetId);
    }

    /**
     * Add fleet to company
     * @param input The fleet to create
     * @param result Fleet validation
     * @param companyId Id of company to create fleet for
     * @return The Response
     */
    @RequestMapping(value = "/companies/{companyId}/fleets", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#companyId, 'company', 'WRITE')")
    public ResponseEntity<?> post(@Valid @RequestBody ApiFleet input, BindingResult result, @PathVariable int companyId) {
        input.setCompany(companyId);
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#fleetId, 'fleet', 'WRITE')")
    public ResponseEntity<?> archiveById(@PathVariable int fleetId) {
        return super.archiveById(fleetId);
    }

    /**
     * Update fleet
     * @param fleetId Fleet to update
     * @param companyId Company this fleet will belong to after update
     * @param input new attributes for the fleet
     * @param result Validation result for fleet
     * @return ResponseEntity
     */
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#fleetId, 'fleet', 'WRITE') && hasPermission(#companyId, 'company', 'WRITE')")
    public ResponseEntity<?> put(
            @PathVariable int fleetId,
            @PathVariable int companyId,
            @Valid @RequestBody ApiFleet input,
            BindingResult result) {
        input.setCompany(companyId);
        return super.put(fleetId, input,result);
    }
}