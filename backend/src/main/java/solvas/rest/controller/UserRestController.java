package solvas.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import solvas.models.Vehicle;

import java.util.Collection;


public class UserRestController {



    public UserRestController() {

    }

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    Collection<Vehicle> getUsers(){
        //TODO make Dao and return list
        return null;
    }
}
