package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.rest.api.models.ApiContract;
import solvas.rest.query.ContractFilter;
import solvas.rest.utils.JsonListWrapper;
import solvas.service.ContractService;
import solvas.service.InsuranceService;
import solvas.service.models.Contract;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Rest controller for route Contracts/
 * Created by domien on 29/03/2017.
 */
@RestController
public class ContractRestController extends AbstractRestController<Contract,ApiContract> {
    private final InsuranceService insuranceService;

    /**
     * Default constructor.
     *
     * @param service service class for entities
     * @param srv InsuranceService service for /contracts/types
     */
    @Autowired
    public ContractRestController(ContractService service, InsuranceService srv) {
        super(service);
        this.insuranceService = srv;
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
    @RequestMapping(value = "/contracts", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, ContractFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }


    @Override
    @RequestMapping(value = "/contracts/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @RequestMapping(value = "/companies/{id}/contracts", method = RequestMethod.GET)
    public ResponseEntity<?> getByCompanyId(@PathVariable int id,Pageable pagination, ContractFilter filter, BindingResult result) {
        filter.setCompany(id);
        return super.listAll(pagination,filter,result);
    }

    @RequestMapping(value = "/companies/{companyId}/fleets/{fleetId}/vehicles/{vehicleId" +
            "}/contracts", method = RequestMethod.GET)
    public ResponseEntity<?> getByCompanyFleetVehicleId(@PathVariable int companyId,@PathVariable int fleetId,@PathVariable int vehicleId,Pageable pagination, ContractFilter filter, BindingResult result) {
        filter.setCompany(companyId);
        filter.setFleet(fleetId);
        filter.setVehicle(vehicleId);
        return super.listAll(pagination,filter,result);
    }

    @Override
    @RequestMapping(value = "/contracts", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody ApiContract input, BindingResult result) {
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/contracts/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> archiveById(@PathVariable int id) {
        return super.archiveById(id);
    }

    @Override
    @RequestMapping(value = "/contracts/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> put(@PathVariable int id, @Valid @RequestBody ApiContract input,BindingResult result) {
        return super.put(id, input,result);
    }

    /**
     * Get all contract types.
     *
     * @return The contract types.
     */
    @RequestMapping(value = "/contracts/types", method = RequestMethod.GET)
    public JsonListWrapper<String> listAllTypes() {
        Collection<String> page = insuranceService.findAllInsuranceTypes();
        JsonListWrapper<String> wrapper = new JsonListWrapper<>(page);
        wrapper.put("total", page.size());
        return wrapper;
    }
}