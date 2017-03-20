package solvas;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.models.Company;
import solvas.persistence.api.dao.CompanyDao;

/**
 * Unfinished root controller, check if necessary
 * TODO make controller for root if necessary
 */
@RestController
public class HomeController {

    @Autowired
    CompanyDao dao2;

    @RequestMapping("/")
    public String index() {

        return "Vop6";
    }

    @RequestMapping("/test")
    public Iterable<Company> lists() {
        return dao2.findAll();
    }
}