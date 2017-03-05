package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Company;
import solvas.persistence.company.CompanyDao;


/**
 * Visit @ http://localhost:8080/companies
 */
@RestController
public class CompanyRestController extends AbstractRestController<Company> {


    /**
     * Rest controller for Company
     * @param dao Autowired
     */
    @Autowired
    public CompanyRestController(CompanyDao dao) {
        super(dao);
    }


    @Override
    @RequestMapping(value = "/companies",method = RequestMethod.GET)
    ResponseEntity<?> listAll() {
        return super.listAll();
    }

    @Override
    @RequestMapping(value = "/companies/{stringId}",method = RequestMethod.GET)
    ResponseEntity<?> getById(@PathVariable String stringId) {
        return super.getById(stringId);
    }

    @Override
    @RequestMapping(value = "/companies",method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody Company input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/companies/{stringId}",method = RequestMethod.DELETE)
    ResponseEntity<?> deleteById(@RequestBody String stringId) {
        return super.deleteById(stringId);
    }

    @Override
    @RequestMapping(value = "/companies",method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody Company input) {
        return super.put(input);
    }
}
