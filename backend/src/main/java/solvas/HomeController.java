package solvas;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.database.Dao;
import solvas.models.Company;
import solvas.models.ManageCompany;

@RestController
public class HomeController {
 
    @RequestMapping("/")
    public String index() {
        //return (new ManageCompany()).addCompany("Ethias", "test")+"";
        return (new Dao<Company>()).find(2).getName();
    }
 
}
