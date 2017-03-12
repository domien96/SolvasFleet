package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Vehicle;
import solvas.persistence.vehicle.VehicleDao;
import solvas.rest.api.mappings.VehicleMapping;
import solvas.rest.api.models.ApiVehicle;

/**
 * Rest controller for Vehicle
 * Visit @ /vehicles
 */
@RestController
public class VehicleRestController extends AbstractRestController<Vehicle,ApiVehicle> {

    /**
     * Rest controller for Vehicle
     *
     * @param dao Autowired
     */
    @Autowired
    public VehicleRestController(VehicleDao dao,VehicleMapping mapping) {
        super(dao,mapping);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.GET)
    public ResponseEntity<?> listAll() {
        return super.listAll("vehicles");
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody ApiVehicle input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody ApiVehicle input) {
        return super.put(input);
    }
}