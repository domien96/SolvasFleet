package solvas.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import solvas.models.Vehicle;

import java.util.Collection;


public class VehicleRestController {



    public VehicleRestController() {

    }

    @RequestMapping(value = "/vehicles",method = RequestMethod.GET)
    Collection<Vehicle> getVehicles(){
        //TODO make Dao and return list
        return null;
    }
}
