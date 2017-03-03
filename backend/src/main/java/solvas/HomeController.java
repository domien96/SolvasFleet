package solvas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.models.Company;
import solvas.persistence.company.CompanyDao;

import java.util.Collection;

@RestController
public class HomeController {

    @Autowired
    private CompanyDao dao;
 
    @RequestMapping("/")
    public Collection<Company> index() {

        String ethias = "Ethias";
        dao.save(new Company(ethias, "nummer"));
        Company c = dao.find(1);
        c.setName("KBC");
        dao.save(c);
        //companyDao.destroy(c);

        // Should not contain ethias company.

        return dao.withName(ethias);
    }
 
}
