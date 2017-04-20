package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.rest.utils.JsonListWrapper;
import solvas.service.models.Vehicle;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.query.VehicleFilter;
import solvas.service.VehicleService;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Rest controller for Vehicle
 * Visit @ /vehicles
 */
@RestController
public class VehicleRestController extends AbstractRestController<Vehicle,ApiVehicle> {

    /**
     * Rest controller for Vehicle
     *
     * @param service service class for vehicles
     */
    @Autowired
    public VehicleRestController(VehicleService service) {
        super(service);
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination The pagination information.
     * @param filter The filters.
     * @param result The validation results of the filterResult
     * @param fleetId The id of the fleet to display vehicles for
     *
     * @return ResponseEntity
     */
    @PreAuthorize("hasPermission(#fleetId, 'fleet', 'READ_VEHICLES')")
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}/vehicles", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, VehicleFilter filter, BindingResult result, @PathVariable int fleetId) {
        filter.setFleet(fleetId);
        return super.listAll(pagination, filter, result);
    }

    /**
     * List all vehicles
     * @param pagination The pagination information.
     * @param filter The filters.
     * @param result The validation results of the filterResult
     * @return ResponseEntity
     */
    @PreAuthorize("hasPermission(0, 'vehicle', 'LIST_VEHICLES')")
    @RequestMapping(value = "/vehicles", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, VehicleFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }

    @Override
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}/vehicles/{vehicleId}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int vehicleId) {
        return super.getById(vehicleId);
    }

    /**
     * Create new vehicle for a fleet
     * @param input ApiVehicle to save
     * @param result Validation result
     * @param fleetId Id of the fleet this vehicle belongs to
     * @return ResponseEntity
     */
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}/vehicles", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#fleetId, 'fleet', 'MANAGE_VEHICLES')")
    public ResponseEntity<?> post(@Valid @RequestBody ApiVehicle input,BindingResult result, @PathVariable int fleetId) {
        input.setFleet(fleetId);
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}/vehicles/{vehicleId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'DELETE')")
    public ResponseEntity<?> archiveById(@PathVariable int vehicleId) {
        return super.archiveById(vehicleId);
    }

    /**
     * Update vehicle
     * @param vehicleId Id of vehicle to update
     * @param fleetId Id of the fleet the vehicle will belong to after update
     * @param input New attributes of the vehicle
     * @param result Validation result
     * @return ResponseEntity
     */
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}/vehicles/{vehicleId}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'EDIT') && hasPermission(#fleetId, 'fleet', 'MANAGE_VEHICLES')")
    public ResponseEntity<?> put(
            @PathVariable int vehicleId,
            @PathVariable int fleetId,
            @Valid @RequestBody ApiVehicle input,
            BindingResult result) {
        input.setFleet(fleetId);
        return super.put(vehicleId, input,result);
    }

    /**
     * Get all vehicle types.
     * @return The vehicle types.
     */
    @RequestMapping(value = "/vehicles/types", method = RequestMethod.GET)
    public JsonListWrapper<String> listAllTypes() {
        Collection<String> page = ((VehicleService) service).findAllVehicleTypes();
        JsonListWrapper<String> wrapper = new JsonListWrapper<>(page);
        wrapper.put("total", page.size());
        return wrapper;
    }

}