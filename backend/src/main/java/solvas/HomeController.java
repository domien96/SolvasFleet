package solvas;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.models.Company;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Unfinished root controller, check if necessary
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public Collection<Company> index() {
        //TODO make controller for root if necessary
        return new ArrayList<>();
    }
}