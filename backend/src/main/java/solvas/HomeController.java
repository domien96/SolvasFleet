package solvas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.persistence.Dao;
import solvas.models.Company;

@RestController
public class HomeController {

    @Autowired
    private Dao dao;
 
    @RequestMapping("/")
    public String index() {

        dao.save(new Company("Ethias", "nummer"));

        Company c = dao.find(Company.class, 1);

        c.setName("KBC");
        dao.save(c);
        //companyDao.destroy(c);

        return c.getName();
    }
 
}
