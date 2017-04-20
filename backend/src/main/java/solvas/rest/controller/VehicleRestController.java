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

    /**
     * Get a vehicle by it's ID. This requires a vehicle to have a fleet (and thus also a company). Vehicles that
     * don't have those, should be accessed using {@link #getById(int)}.
     *
     * @param companyId The ID of the company.
     * @param fleetId The ID of the fleet.
     * @param vehicleId The ID of the vehicle.
     *
     * @return The response.
     */
    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}/vehicles/{vehicleId}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int companyId, @PathVariable int fleetId, @PathVariable int vehicleId) {
        return super.getById(vehicleId);
    }

    @Override
    @RequestMapping(value = "/vehicles/{vehicleId}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int vehicleId) {
        return super.getById(vehicleId);
    }

    /**
     * Create new vehicle.
     * @param input ApiVehicle to save
     * @param result Validation result
     * @return ResponseEntity
     */
    @Override
    @RequestMapping(value = "/vehicles", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#input.fleet, 'fleet', 'MANAGE_VEHICLES')")
    public ResponseEntity<?> post(@Valid @RequestBody ApiVehicle input, BindingResult result) {
        return super.post(input,result);
    }

    @Override
    @RequestMapping(value = "/vehicles/{vehicleId}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'DELETE')")
    public ResponseEntity<?> archiveById(@PathVariable int vehicleId) {
        return super.archiveById(vehicleId);
    }

    /**
     * Update vehicle
     * @param vehicleId Id of vehicle to update
     * @param input New attributes of the vehicle
     * @param result Validation result
     * @return ResponseEntity
     */
    @Override
    @RequestMapping(value = "/vehicles/{vehicleId}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'EDIT') && hasPermission(#input.fleet, 'fleet', 'MANAGE_VEHICLES')")
    public ResponseEntity<?> put(
            @PathVariable int vehicleId,
            @Valid @RequestBody ApiVehicle input,
            BindingResult result) {
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