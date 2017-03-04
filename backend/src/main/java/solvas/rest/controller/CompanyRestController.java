package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Company;
import solvas.persistence.company.CompanyDao;

import java.util.Collection;


/**
 * Visit @ http://localhost:8080/companies
 *
 */
@RestController
public class CompanyRestController {


    private CompanyDao dao;

    @Autowired
    public CompanyRestController(CompanyDao dao) {
        this.dao=dao;
    }

    @RequestMapping(value = "/companies",method = RequestMethod.GET)
    Collection<Company> getCompanies(){
        return dao.findAll();
    }


    @RequestMapping(value = "/companies",method = RequestMethod.PUT)
    ResponseEntity<?> putCompanies(@RequestBody Company input){
        //http://stackoverflow.com/questions/797834/should-a-restful-put-operation-return-something
        Company update = dao.find(input.getId());
        HttpStatus status;
        if (update==null){
            update = dao.save(input);
            status = HttpStatus.OK;
        } else {

        }
        return null; // TODO fix return
    }


    @RequestMapping(value = "/companies/{companyId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> getCompany(@PathVariable String companyId) {
        Company companyById;
        int id;

        //TODO replace by faster
        try {
            id = Integer.parseInt(companyId);
        } catch (NumberFormatException e){
            // TODO: make 404's have proper JSON bodies
            return new ResponseEntity<>("Id not valid", HttpStatus.NOT_FOUND);
        }
        companyById = dao.find(id);
        if(companyById == null) {
            return new ResponseEntity<>("Company not found", HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<>(companyById, HttpStatus.OK);
    }

    @RequestMapping(value = "/companies",method = RequestMethod.POST)
    ResponseEntity<?> addCompany(@RequestBody Company input) {
        //Post message met aplication/json {"name":"comp4","vat":"4"}
        if (input.getName()!=null && input.getVat_number() != null){
            dao.save(input);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }




}
