package solvas.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Company;

import java.util.Collection;
import java.util.HashMap;

//Visit @ http://localhost:8080/companies
@RestController
public class CompanyRestController {

    private int id=0;
    private HashMap<Integer,Company> companies = new HashMap<>();
    public CompanyRestController() {
        //emulate db
        Company company =new Company("comp1","1");
        company.setId(1);
        companies.put(1,company);
        company =new Company("comp2","1");
        company.setId(2);
        companies.put(2,company);
        company =new Company("comp3","2");
        company.setId(3);
        companies.put(3,company);
        id =4;
    }

    @RequestMapping(value = "/companies",method = RequestMethod.GET)
    Collection<Company> getCompanies(){
        return companies.values();
    }

    @RequestMapping(value = "/companies/{companyId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<?> getCompany(@PathVariable String companyId) {
        Company company = companies.get(Integer.valueOf(companyId));
        if(company == null) {
            // TODO: make 404's have proper JSON bodies
            return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(company, HttpStatus.OK);
    }

    @RequestMapping(value = "/companies",method = RequestMethod.POST)
    ResponseEntity<?> addCompany(@RequestBody Company input) {
        //Post message met aplication/json {"name":"comp4","vat":"4"}
        System.out.println("id="+input.getId());
        System.out.println("name="+input.getName());
        System.out.println("vat="+input.getVat());


        if (input.getName()!=null && input.getVat() != null){
            input.setId(id++);
            companies.put(input.getId(),input);
            return new ResponseEntity<>(HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
