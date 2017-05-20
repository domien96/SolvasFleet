package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> postVehicleCommission(@Valid @RequestBody ApiCommission input
            ,@PathVariable int id, @PathVariable String type, BindingResult result) {
        input.setInsuranceType(type);
        input.setVehicle(id);
        return super.post(input, result);
    }

    /**
     * A Subfleet(fleet and vehicle type) has the second highest priority
     * @param input value to be inserted
     * @param fleetId id of the fleet
     * @param insuranceType vehicle type
     * @param result  The binding to use to validate
     * @return Response with the saved model, or 400.
     */
    @RequestMapping(value = "/commissions/fleet/{fleetId}/type/{fleetType}/type/{insuranceType}", method = RequestMethod.POST)
    public ResponseEntity<?> postFleetAndTypeCommission(@Valid @RequestBody ApiCommission input
            ,@PathVariable int fleetId, @PathVariable String fleetType, @PathVariable String insuranceType, BindingResult result) {
        input.setInsuranceType(insuranceType);
        input.setVehicleType(fleetType);
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
    @RequestMapping(value = "/commissions/fleet/{fleetId}/type/{insuranceType}", method = RequestMethod.POST)
    public ResponseEntity<?> postFleetCommission(@Valid @RequestBody ApiCommission input
            ,@PathVariable int fleetId ,@PathVariable String insuranceType , BindingResult result) {
        input.setInsuranceType(insuranceType);
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
    @RequestMapping(value = "/commissions/company/{companyId}/type/{insuranceType}", method = RequestMethod.POST)
    public ResponseEntity<?> postCompanyCommission(@Valid @RequestBody ApiCommission input
            ,@PathVariable int companyId,@PathVariable String insuranceType, BindingResult result) {
        input.setVehicleType(null);
        input.setVehicle(0);
        input.setCompany(companyId);
        input.setInsuranceType(insuranceType);
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
    public ResponseEntity<?> listAll(Pageable pagination, CommissionFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }

}