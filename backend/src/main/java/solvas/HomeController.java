package solvas;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.database.Dao;
import solvas.models.Company;

@RestController
public class HomeController {
 
    @RequestMapping("/")
    public String index() {
        Dao<Company> companyDao = new Dao<>(Company.class);

        companyDao.save(new Company("Ethias", "nummer"));

        Company c = companyDao.find(1 );

        c.setName("KBC");
        companyDao.save(c);
        //companyDao.destroy(c);

        return c.getName();
    }
 
}
