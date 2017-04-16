package solvas.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiTax;
import solvas.rest.query.TaxFilter;
import solvas.rest.utils.JsonListWrapper;
import solvas.service.ContractService;
import solvas.service.TaxService;
import solvas.service.models.Tax;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Rest endpoints for taxes
 * Created by steve on 16/04/2017.
 */
@RestController
public class TaxRestController extends AbstractRestController<Tax,ApiTax>{


    /**
     * Default constructor.
     *
     * @param service service class for entities
     */
    protected TaxRestController(TaxService service) {
        super(service);
    }


    /**
     * Get tax with vehicle type and contract type
     *
     *  @param vehicleType vehicle type for the tax
     *  @param contractType insurance type for the tax
     *  @param filter autowired filter
     *
     * @return The current tax
     */
    @RequestMapping(value = "/vehicles/types/{vehicleType}/taxes/{contractType}", method = RequestMethod.GET)
    public ResponseEntity<?> getTax(@PathVariable String vehicleType, @PathVariable String contractType, TaxFilter filter) {
        filter.setContractType(contractType);
        filter.setVehicleType(vehicleType);
        List<ApiTax> result = service.findAll(filter);
        if (result.size()==1) {
            return new ResponseEntity<>(service.findAll(filter).iterator().next(), HttpStatus.OK);
        } else {
            return notFound();
        }
    }


    /**
     * Put tax with vehicle type and contract type
     *  @param vehicleType vehicle type for the tax
     *  @param contractType insurance type for the tax
     *  @param input input of the endpoint
     *  @param result binding result
     *
     * @return The contract types.
     */
    @RequestMapping(value = "/vehicles/types/{vehicleType}/taxes/{contractType}", method = RequestMethod.PUT)
    public ResponseEntity<?> putTax(@PathVariable String vehicleType, @PathVariable String contractType,
                                    @Valid @RequestBody ApiTax input, BindingResult result) {
        input.setVehicleType(vehicleType);
        input.setContractType(contractType);
        return super.put(0,input,result);
    }

}
