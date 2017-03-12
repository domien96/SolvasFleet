package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.models.Company;
import solvas.models.validators.CompanyValidator;
import solvas.persistence.company.CompanyDao;
import solvas.rest.api.mappers.CompanyMapper;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.query.CompanyFilter;
import solvas.rest.query.PaginationFilter;


/**
 * Rest controller for Company
 * Visit @ /companies
 */
@RestController
public class CompanyRestController extends AbstractRestController<Company,ApiCompany> {

    /**
     * Rest controller for Company
     *
     * @param dao Autowired
     * @param mapper The mapper class for companies
     * @param validator Validator for companies
     */
    @Autowired
    public CompanyRestController(CompanyDao dao, CompanyMapper mapper, CompanyValidator validator) {
        super(dao, mapper, validator);
    }




    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination The pagination information.
     * @param filter The filters.
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(PaginationFilter pagination, CompanyFilter filter) {
        return super.listAll(pagination, filter);
    }

    @Override
    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody ApiCompany input, BindingResult result) {
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
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody ApiCompany input,BindingResult result) {
        return super.put(id, input,result);
    }
}