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
 * No POST request are allowed.
 * If you want to add a commission, you use the PUT method. This is because an endpoint user does not have to know
 * whether a commission already exists for the specific parameters or not. The endpoint user may assume that for each
 * set of parameters a commission exists.
 * Visit @ /commissions
 * @author sjabasti
 * @author domien
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
        //commissionService.destroy(id);
        //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // Maybe this will be required later.
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }


    @RequestMapping(value = "/commissions", method = RequestMethod.PUT)
    public ResponseEntity<?> changeVehicleCommission(@Valid @RequestBody ApiCommission input, BindingResult result) {
        return super.put(input.getId(),input, result);
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