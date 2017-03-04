package solvas.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import solvas.models.Vehicle;

import java.util.Collection;


public class RoleRestController {



    public RoleRestController() {

    }

    @RequestMapping(value = "/roles",method = RequestMethod.GET)
    Collection<Vehicle> getRoles(){
        //TODO make Dao and return list
        return null;
    }
}
