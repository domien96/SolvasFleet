package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.service.CommissionService;
import solvas.service.models.Commission;
import solvas.service.models.Company;
import solvas.rest.api.models.ApiCommission;
import solvas.rest.query.CompanyFilter;
import solvas.service.CompanyService;

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


    

    @Override
    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#input, 'CREATE')")
    public ResponseEntity<?> post(@Valid @RequestBody ApiCompany input, BindingResult result) {
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'company', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'company', 'DELETE')")
    public ResponseEntity<?> archiveById(@PathVariable int id) {
        return super.archiveById(id);
    }

    @Override
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#id, 'company', 'EDIT')")
    public ResponseEntity<?> put(@PathVariable int id, @Valid @RequestBody ApiCompany input,BindingResult result) {
        return super.put(id, input,result);
    }
}