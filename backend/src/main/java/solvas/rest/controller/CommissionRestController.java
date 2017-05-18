package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.query.CommissionFilter;
import solvas.service.CommissionService;
import solvas.service.exceptions.UndeletableException;
import solvas.service.models.Commission;
import solvas.rest.api.models.ApiCommission;

import javax.validation.Valid;


/**
 * Rest controller for Commission
 * Visit @ /commissions
 */
@RestController
public class CommissionRestController extends AbstractRestController<Commission,ApiCommission> {


    private CommissionService commissionService;

    /**
     * Rest controller for Commission
     *
     * @param service service class for Commissions
     */
    @Autowired
    public CommissionRestController(CommissionService service) {
        super(service);
        commissionService=service;
    }

    @RequestMapping(value = "/commissions/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#id, 'commission', 'EDIT')")
    public ResponseEntity<?> put(@PathVariable int id, @Valid @RequestBody ApiCommission input, BindingResult result) {
        return super.put(id, input,result);
    }

    /**
     * Delete a Commission.
     *
     * @param id The Commission's ID.
     *
     * @return Nothing, empty response.
     *
     * @throws EntityNotFoundException If the Commission was not found.
     */
    @RequestMapping(value = "/commissions/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'commission', 'READ')")
    public ResponseEntity<?> deleteById(@PathVariable int id) throws EntityNotFoundException, UndeletableException {
        commissionService.destroy(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * A Vehicle has the highest priority
     * @param input value to be inserted
     * @param id id of the vehicle
     * @param result  The binding to use to validate
     * @return Response with the saved model, or 400.
     */
    @RequestMapping(value = "/commissions/vehicle/{id}/type/{type}", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#input, 'CREATE')")
    public ResponseEntity<?> postVehicleCommission(@Valid @RequestBody ApiCommission input
            ,@PathVariable int id, @PathVariable String type, BindingResult result) {
        input.setInsuranceType(type);
        input.setVehicleType(null);
        input.setVehicle(id);
        input.setCompany(0);
        input.setFleet(0);
        return super.post(input, result);
    }

    /**
     * A Subfleet(fleet and vehicle type) has the second highest priority
     * @param input value to be inserted
     * @param fleetId id of the fleet
     * @param type vehicle type
     * @param result  The binding to use to validate
     * @return Response with the saved model, or 400.
     */
    @RequestMapping(value = "/commissions/fleet/{fleetId}/type/{fleetType}/type/{type}", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#input, 'CREATE')")
    public ResponseEntity<?> postFleetAndTypeCommission(@Valid @RequestBody ApiCommission input
            ,@PathVariable int fleetId, @PathVariable String fleetType, @PathVariable String type, BindingResult result) {
        input.setInsuranceType(type);
        input.setVehicleType(fleetType);
        input.setVehicle(0);
        input.setCompany(0);
        input.setFleet(fleetId);
        return super.post(input, result);
    }


    /**
     * A fleet has the third highest priority
     * @param input value to be inserted
     * @param fleetId id of the fleet
     * @param result  The binding to use to validate
     * @return Response with the saved model, or 400.
     */
    @RequestMapping(value = "/commissions/fleet/{fleetId}/type/{type}", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#input, 'CREATE')")
    public ResponseEntity<?> postFleetCommission(@Valid @RequestBody ApiCommission input
            ,@PathVariable int fleetId ,@PathVariable String type , BindingResult result) {
        input.setVehicleType(null);
        input.setInsuranceType(type);
        input.setVehicle(0);
        input.setCompany(0);
        input.setFleet(fleetId);
        return super.post(input, result);
    }

    /**
     * A company has the forth highest priority
     * @param input value to be inserted
     * @param companyId id of the company
     * @param result  The binding to use to validate
     * @return Response with the saved model, or 400.
     */
    @RequestMapping(value = "/commissions/company/{companyId}/type/{type}", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#input, 'CREATE')")
    public ResponseEntity<?> postCompanyCommission(@Valid @RequestBody ApiCommission input
            ,@PathVariable int companyId,@PathVariable String type, BindingResult result) {
        input.setVehicleType(null);
        input.setVehicle(0);
        input.setCompany(companyId);
        input.setInsuranceType(type);
        input.setFleet(0);
        return super.post(input, result);
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
    @RequestMapping(value = "/commissions", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(0, 'commission', 'READ')")
    public ResponseEntity<?> listAll(Pageable pagination, CommissionFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }

}