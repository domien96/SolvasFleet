package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import solvas.models.Company;
import solvas.models.validators.CompanyValidator;
import solvas.persistence.company.CompanyDao;


/**
 * Rest controller for Company
 * Visit @ /companies
 */
@RestController
public class CompanyRestController extends AbstractRestController<Company> {

    /**
     * Rest controller for Company
     *
     * @param dao Autowired
     * @param validator Validator for companies
     */
    @Autowired
    public CompanyRestController(CompanyDao dao, CompanyValidator validator) {
        super(dao, validator);
    }

    @Override
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        return super.listAll("companies");
    }

    @Override
    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody Company input, BindingResult result) {
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
    @RequestMapping(value = "/companies", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody Company input, BindingResult result) {
        return super.put(input, result);
    }
}