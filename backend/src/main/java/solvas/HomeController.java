package solvas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.models.Company;
import solvas.persistence.company.CompanyDao;

import java.util.Collection;
import java.util.Collections;

/**
 * Unfinished root controller, check if necessary
 */
@RestController
public class HomeController {

    @Autowired
    CompanyDao companyDao;

    @RequestMapping("/")
    public Collection<Company> index() {
        Company company = new Company();
        company.setName("Name");
        company.setVatNumber("Vat");
        company.setPhoneNumber("phone");
        company.setAddressCity("city");
        company.setAddressCountry("country");
        company.setAddressHouseNumber("housenumber");
        company.setAddressPostalCode("postalcode");
        company.setAddressStreet("street");
        companyDao.save(company);


        //TODO make controller for root if necessary
        return Collections.emptyList();
    }
}