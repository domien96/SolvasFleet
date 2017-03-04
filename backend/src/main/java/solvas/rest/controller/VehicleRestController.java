package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Vehicle;
import solvas.persistence.vehicle.VehicleDao;

import java.util.Collection;

@RestController
public class VehicleRestController extends AbstractRestController<Vehicle> {


    @Autowired
    public VehicleRestController(VehicleDao dao) {
        super(dao);
    }

    @Override
    @RequestMapping(value = "/vehicles",method = RequestMethod.GET)
    ResponseEntity<?> get() {
        return super.get();
    }

    @Override
    @RequestMapping(value = "/vehicles/{s_id}",method = RequestMethod.GET)
    ResponseEntity<?> getId(@PathVariable String s_id) {
        return super.getId(s_id);
    }

    @Override
    @RequestMapping(value = "/vehicles",method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody Vehicle input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/vehicles/{s_id}",method = RequestMethod.DELETE)
    ResponseEntity<?> deleteId(@RequestBody String s_id) {
        return super.deleteId(s_id);
    }

    @Override
    @RequestMapping(value = "/vehicles",method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody Vehicle input) {
        return super.put(input);
    }
}