package solvas;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.models.ManageCompany;

@RestController
public class HomeController {
 
    @RequestMapping("/")
    public String index() {
        return (new ManageCompany()).addCompany("Ethias", "test")+"";
    }
 
}
