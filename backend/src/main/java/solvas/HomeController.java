package solvas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.models.Company;
import solvas.persistence.company.CompanyDao;

import java.util.ArrayList;
import java.util.Collection;

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
        return new ArrayList<>();
    }
 
}
