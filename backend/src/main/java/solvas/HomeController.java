package solvas;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import solvas.models.Company;

import java.util.Collection;
import java.util.Collections;

/**
 * Unfinished root controller, check if necessary
 * TODO make controller for root if necessary
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public String index() {

        return "Vop6";
    }
}