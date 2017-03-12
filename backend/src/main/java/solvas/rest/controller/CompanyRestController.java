package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Company;
import solvas.persistence.company.CompanyDao;
import solvas.rest.api.mappings.CompanyMapping;
import solvas.rest.api.models.ApiModel;
import solvas.rest.utils.JsonListWrapper;

import java.util.Collection;
import java.util.HashSet;


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


    @Override
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        CompanyMapping mapping = new CompanyMapping();
        Collection<ApiModel> collection = new HashSet<>();
        for (Company item: dao.findAll()){
            collection.add(mapping.convertToApiModel(item));
        }
        return new ResponseEntity<>(new JsonListWrapper<ApiModel>(collection, "companies"), HttpStatus.OK);


        //return super.listAll("companies");
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