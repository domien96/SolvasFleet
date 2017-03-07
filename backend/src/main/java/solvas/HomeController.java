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

    private final CompanyDao dao;

    @Autowired
    public HomeController(CompanyDao dao) {
        this.dao = dao;
    }

    @RequestMapping("/")
    public Collection<Company> index() {
        //TODO make controller for root if necessary

        Company company = new Company();
        company.setName("test");
        company.setAddress("lol");
        dao.save(company);
//        Company company = dao.find(20);
//        company.setName("Test 22222222");
//        company.setAddress("lololol");

       // dao.save(company);

        return Collections.singleton(company);
    }
}