package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Company;
import solvas.persistence.company.CompanyDao;
import solvas.rest.query.CompanyFilter;
import solvas.rest.query.PaginationFilter;


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
     */
    @Autowired
    public CompanyRestController(CompanyDao dao) {
        super(dao);
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
    public ResponseEntity<?> post(@RequestBody Company input) {
        return super.post(input);
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
    public ResponseEntity<?> put(@RequestBody Company input) {
        return super.put(input);
    }
}