package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import solvas.models.Vehicle;
import solvas.persistence.vehicle.VehicleDao;
import solvas.rest.query.PaginationFilter;
import solvas.rest.query.VehicleFilter;

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
    public ResponseEntity<?> listAll(PaginationFilter pagination, VehicleFilter filter) {
        return super.listAll(pagination, filter);
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody Vehicle input) {
        return super.post(input);
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody Vehicle input) {
        return super.put(input);
    }
}