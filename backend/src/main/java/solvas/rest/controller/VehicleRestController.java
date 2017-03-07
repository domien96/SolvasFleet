package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.models.Vehicle;
import solvas.models.validators.VehicleValidator;
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
    public VehicleRestController(VehicleDao dao, VehicleValidator validator) {
        super(dao, validator);
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
    public ResponseEntity<?> post(@RequestBody Vehicle input, BindingResult result) {
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@RequestBody Vehicle input, BindingResult result) {
        return super.put(input, result);
    }
}