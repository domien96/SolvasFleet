package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.VehicleAbstractMapper;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.query.PaginationFilter;
import solvas.rest.query.VehicleFilter;

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
     * @param validator Validator for vehicles
     */
    @Autowired
    public VehicleRestController(DaoContext daoContext, VehicleAbstractMapper mapper) {
        super(daoContext.getVehicleDao(),mapper, null);
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination The pagination information.
     * @param paginationResult The validation results of the pagination object.
     * @param filter The filters.
     * @param result The validation results of the filterResult
     *
     * @return ResponseEntity
     */
    @RequestMapping(value = "/vehicles", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(PaginationFilter pagination, BindingResult paginationResult, VehicleFilter filter, BindingResult result) {
        return super.listAll(pagination, paginationResult, filter, result);
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.POST)
    public ResponseEntity<?> post(@RequestBody ApiVehicle input,BindingResult result) {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        return super.deleteById(id);
    }

    @Override

    @RequestMapping(value = "/vehicles/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @RequestBody ApiVehicle input,BindingResult result) {
        return super.put(id, input,result);
    }
}