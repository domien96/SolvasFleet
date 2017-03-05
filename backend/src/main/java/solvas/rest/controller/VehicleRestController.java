package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Vehicle;
import solvas.persistence.vehicle.VehicleDao;

/**
 * Rest controller for Vehicle
 * Visit @ /vehicles
 *
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
    ResponseEntity<?> listAll() {
        return super.listAll();
    }

    @Override
    @RequestMapping(value = "/vehicles/{stringId}",method = RequestMethod.GET)
    ResponseEntity<?> getById(@PathVariable String stringId) {
        return super.getById(stringId);
    }

    @Override
    @RequestMapping(value = "/vehicles",method = RequestMethod.POST)
    ResponseEntity<?> post(@RequestBody Vehicle input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/vehicles/{stringId}",method = RequestMethod.DELETE)
    ResponseEntity<?> deleteById(@RequestBody String stringId) {
        return super.deleteById(stringId);
    }

    @Override
    @RequestMapping(value = "/vehicles",method = RequestMethod.PUT)
    ResponseEntity<?> put(@RequestBody Vehicle input) {
        return super.put(input);
    }
}