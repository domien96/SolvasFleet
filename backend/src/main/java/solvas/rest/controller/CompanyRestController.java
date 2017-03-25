package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.models.Company;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.query.CompanyFilter;
import solvas.rest.query.PaginationFilter;
import solvas.rest.service.CompanyService;

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
     * @param paginationResult The validation results of the pagination object.
     * @param filter The filters.
     * @param result The validation results of the filterResult
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(PaginationFilter pagination, BindingResult paginationResult, CompanyFilter filter, BindingResult result) {
        return super.listAll(pagination, paginationResult, filter, result);
    }

    @Override
    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody ApiCompany input, BindingResult result) {
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @Valid @RequestBody ApiCompany input,BindingResult result) {
        return super.put(id, input,result);
    }
}