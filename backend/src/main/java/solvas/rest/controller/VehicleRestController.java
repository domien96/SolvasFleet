package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Vehicle;
import solvas.persistence.vehicle.VehicleDao;

/**
 * Rest controller for Vehicle
 * Visit @ /vehicles
 */
@RestController
public class VehicleRestController extends AbstractRestController<Vehicle> {

    /**
     * Rest controller for Vehicle
     *
     * @param dao Autowired
     */
    @Autowired
    public VehicleRestController(VehicleDao dao) {
        super(dao);
    }

    @RequestMapping(value = "/vehicles", method = RequestMethod.GET)
    ResponseEntity<?> listAll() {
        return super.listAll("vehicles");
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody Vehicle input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody Vehicle input) {
        return super.put(input);
    }
}