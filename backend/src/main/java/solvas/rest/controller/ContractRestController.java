package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiContract;
import solvas.rest.query.ContractFilter;
import solvas.rest.utils.JsonListWrapper;
import solvas.service.ContractService;
import solvas.service.exceptions.UnarchivableException;
import solvas.service.models.Contract;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Rest controller for route Contracts/
 * Created by domien on 29/03/2017.
 */
@RestController
public class ContractRestController extends AbstractRestController<Contract,ApiContract> {

    /**
     * Default constructor.
     *
     * @param service service class for entities
     */
    @Autowired
    public ContractRestController(ContractService service) {
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
    @RequestMapping(value = "/contracts", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(0, 'contract', 'READ')")
    public ResponseEntity<?> listAll(Pageable pagination, ContractFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }


    @Override
    @RequestMapping(value = "/contracts/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'contract', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    /**
     * Get all contracts for a company.
     *
     * @param id The ID of the company.
     * @param pagination The pagination.
     * @param filter The filters.
     * @param result The validation results.
     *
     * @return The response.
     */
    @RequestMapping(value = "/companies/{id}/contracts", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'company', 'READ')")
    public ResponseEntity<?> getByCompanyId(@PathVariable int id,Pageable pagination, ContractFilter filter, BindingResult result) {
        filter.setClientCompany(id);
        return super.listAll(pagination,filter,result);
    }

    /**
     * Get all contracts for a vehicle in a fleet from a company.
     *
     * @param companyId ID of the company.
     * @param fleetId ID of the fleet.
     * @param vehicleId ID of the vehicle.
     * @param pagination The pagination.
     * @param filter The filters.
     * @param result The validation results.
     *
     * @return The response.
     */
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}/vehicles/{vehicleId" +
            "}/contracts", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'READ') && hasPermission(#companyId, 'company', 'READ') && hasPermission(#fleetId, 'fleet', 'READ')")
    public ResponseEntity<?> getByCompanyFleetVehicleId(@PathVariable int companyId,@PathVariable int fleetId,@PathVariable int vehicleId,Pageable pagination, ContractFilter filter, BindingResult result) {
        filter.setClientCompany(companyId);
        filter.setFleet(fleetId);
        filter.setVehicle(vehicleId);
        return super.listAll(pagination,filter,result);
    }

    // TODO: authorize
    @Override
    @RequestMapping(value = "/contracts", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(0, 'contract', 'CREATE')")
    public ResponseEntity<?> post(@Valid @RequestBody ApiContract input, BindingResult result) {
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/contracts/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'contract', 'DELETE')")
    public ResponseEntity<?> archiveById(@PathVariable int id) throws EntityNotFoundException, UnarchivableException {
        return super.archiveById(id);
    }

    @Override
    @RequestMapping(value = "/contracts/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#id, 'contract', 'EDIT')")
    public ResponseEntity<?> put(@PathVariable int id, @Valid @RequestBody ApiContract input,BindingResult result) {
        return super.put(id, input,result);
    }

    /**
     * Get all contract types.
     *
     * @return The contract types.
     */
    @RequestMapping(value = "/contracts/types", method = RequestMethod.GET)
    public JsonListWrapper<String> listAllTypes() {
        Collection<String> items = ((ContractService) service).findAllInsuranceTypes();
        return JsonListWrapper.withTotal(items);
    }
}