package solvas.rest.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.greencard.GreenCardViewResolver;
import solvas.rest.greencard.pdf.GreenCardPdfView;
import solvas.rest.utils.JsonListWrapper;
import solvas.service.models.Vehicle;
import org.springframework.web.multipart.MultipartFile;
import solvas.rest.api.models.ApiVehicle;
import solvas.rest.api.models.errors.ApiError;
import solvas.rest.query.VehicleFilter;
import solvas.rest.utils.JsonListWrapper;
import solvas.rest.utils.MyCsvToBean;
import solvas.service.VehicleService;
import solvas.service.models.Vehicle;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rest controller for Vehicle
 * Visit @ /vehicles
 */
@RestController
public class VehicleRestController extends AbstractRestController<Vehicle,ApiVehicle> {

    private final Validator validator;

    /**
     * Rest controller for Vehicle
     *
     * @param service service class for vehicles
     * @param validator The validator, used for validating the csv import
     */
    @Autowired
    public VehicleRestController(VehicleService service,  @Qualifier("mvcValidator") Validator validator) {
        super(service);
        this.validator = validator;
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
     * Get green card of a vehicle as pdf.
     * @param vehicleId Id of vehicle
     * @return ResponseEntity
     */
    @RequestMapping(value = "/vehicles/{vehicleId}/greencard.pdf", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'READ')")
    public ModelAndView getByFleetAndInvoiceIdWithExtension(@PathVariable int vehicleId) throws EntityNotFoundException {
        ApiVehicle v = service.getById(vehicleId);
        return new ModelAndView(GreenCardViewResolver.GREEN_CARD_PDF_VIEW, GreenCardPdfView.class.getCanonicalName(), v);
    }

    /**
     * Create new vehicle
     * @param input ApiVehicle to save
     * @param result Validation result
     * @return ResponseEntity
     */
    @RequestMapping(value = "/vehicles", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#input, 'CREATE')")
    public ResponseEntity<?> post(@Valid @RequestBody ApiVehicle input,BindingResult result) {
        return super.post(input,result);
    }

    /**
     * Create multiple vehicles using a csv file.
     * The Csv file must be included within the request.
     * @param file  A CSV file containing the new vehicles
     *              The csv column names must match with the column names used in the API.
     *              The order of the columns does not matter, but the file must have a header line.
     * @return ResponseEntity
     */
    @PostMapping("/vehicles/upload")
    @PreAuthorize("hasPermission(0, 'vehicle', 'IMPORT_VEHICLES')")
    public ResponseEntity<?> importCSV(@RequestParam MultipartFile file) throws JsonProcessingException {
        CSVReader csvReader;
        try {
            csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace(); //todo map to correct error code
            throw new RuntimeException();
        }
        CsvToBean<ApiVehicle> csv = new MyCsvToBean<>();
        //Set column mapping strategy
        List<ApiVehicle> list = csv.parse(setColumnMapping(), csvReader);
        //todo parse exception correct afhandelen

        // Maps the row to a potential list of errors.
        Map<Integer, List<ApiError>> bundledErrors = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            ApiVehicle vehicle = list.get(i);
            BindingResult errors = new BeanPropertyBindingResult(vehicle,"vehicle");
            validator.validate(vehicle, errors);
            if (errors.hasErrors()) {
                // Row counting starts at 1 (not zero)
                bundledErrors.put(i + 1, ApiError.convertToApiErrors(errors));
            }
        }

        if (bundledErrors.isEmpty()) {
            list.forEach(v -> super.post(v, new BeanPropertyBindingResult("", ""))); // dummy bindingresult
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(
                    new JsonListWrapper<>(bundledErrors.entrySet(), JsonListWrapper.ERROR_KEY),
                    HttpStatus.BAD_REQUEST);
        }
    }

    private MappingStrategy<ApiVehicle> setColumnMapping()
    {
        // Order does not matter for this strategy, but requires a header line with the column names.
        HeaderColumnNameTranslateMappingStrategy<ApiVehicle> strategy = new HeaderColumnNameTranslateMappingStrategy<>();
        strategy.setType(ApiVehicle.class);
        Map<String,String> columnMappings = new HashMap<>();
        columnMappings.put("licensePlate", "licensePlate");
        columnMappings.put("leasingCompany", "leasingCompany");
        columnMappings.put("fleet", "fleet");
        columnMappings.put("mileage", "mileage");
        columnMappings.put("type", "type");
        columnMappings.put("value", "value");
        columnMappings.put("year", "year");
        columnMappings.put("vin", "vin");
        columnMappings.put("model","model");
        columnMappings.put("brand","brand");
        strategy.setColumnMapping(columnMappings);
        return strategy;
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
    @PreAuthorize("hasPermission(#vehicleId, 'vehicle', 'EDIT')")
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