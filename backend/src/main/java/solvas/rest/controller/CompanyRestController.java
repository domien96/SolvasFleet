package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Company;
import solvas.models.Role;
import solvas.persistence.Role.RoleDao;
import solvas.persistence.company.CompanyDao;


import java.util.Collection;


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
    ResponseEntity<?> get() {
        return super.get();
    }

    @Override
    @RequestMapping(value = "/companies/{stringId}",method = RequestMethod.GET)
    ResponseEntity<?> getId(@PathVariable String stringId) {
        return super.getId(stringId);
    }

    @Override
    @RequestMapping(value = "/companies",method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody Company input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/companies/{stringId}",method = RequestMethod.DELETE)
    ResponseEntity<?> deleteId(@RequestBody String stringId) {
        return super.deleteId(stringId);
    }

    @Override
    @RequestMapping(value = "/companies",method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody Company input) {
        return super.put(input);
    }
}
