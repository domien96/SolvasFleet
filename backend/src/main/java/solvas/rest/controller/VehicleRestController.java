package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Vehicle;
import solvas.persistence.vehicle.VehicleDao;

import java.util.Collection;

/**
 * Visit @ http://localhost:8080/vehicles
 */
@RestController
public class VehicleRestController extends AbstractRestController<Vehicle> {


    /**
     * Rest controller for Vehicle
     * @param dao Autowired
     */
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
    @RequestMapping(value = "/vehicles/{stringId}",method = RequestMethod.GET)
    ResponseEntity<?> getId(@PathVariable String stringId) {
        return super.getId(stringId);
    }

    @Override
    @RequestMapping(value = "/vehicles",method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody Vehicle input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/vehicles/{stringId}",method = RequestMethod.DELETE)
    ResponseEntity<?> deleteId(@RequestBody String stringId) {
        return super.deleteId(stringId);
    }

    @Override
    @RequestMapping(value = "/vehicles",method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody Vehicle input) {
        return super.put(input);
    }
}