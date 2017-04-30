package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.service.models.Company;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.query.CompanyFilter;
import solvas.service.CompanyService;

import javax.validation.Valid;


/**
 * Rest controller for Company
 * Visit @ /companies
 */
@RestController
public class CompanyRestController extends AbstractRestController<Company,ApiCompany> {

    /**
     * Rest controller for Company
     *
     * @param service service class for companies
     */
    @Autowired
    public CompanyRestController(CompanyService service) {
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
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, CompanyFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
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