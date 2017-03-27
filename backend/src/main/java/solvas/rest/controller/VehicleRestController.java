package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.VehicleAbstractMapper;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.query.VehicleFilter;

import javax.validation.Valid;

/**
 * Rest controller for Vehicle
 * Visit @ /vehicles
 */
@RestController
public class VehicleRestController extends AbstractRestController<Vehicle,ApiVehicle> {

    /**
     * Rest controller for Vehicle
     *
     * @param daoContext Autowired
     * @param mapper The mapper class for vehicles
     */
    @Autowired
    public VehicleRestController(DaoContext daoContext, VehicleAbstractMapper mapper) {
        super(daoContext.getVehicleDao(),mapper);
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination The pagination information.
     * @param filter The filters.
     * @param result The validation results of the filterResult
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/vehicles", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, VehicleFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody ApiVehicle input,BindingResult result) {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override

    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @Valid @RequestBody ApiVehicle input, BindingResult result) {
        return super.put(id, input,result);
    }
}