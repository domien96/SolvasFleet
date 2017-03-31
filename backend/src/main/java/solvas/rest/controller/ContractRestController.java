package solvas.rest.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.api.models.ApiContract;
import solvas.rest.query.CompanyFilter;
import solvas.rest.query.ContractFilter;
import solvas.service.AbstractService;
import solvas.service.models.Contract;

import javax.validation.Valid;

/**
 * Rest controller for route Contracts/
 * Created by domien on 29/03/2017.
 */
public class ContractRestController extends AbstractRestController<Contract,ApiContract> {
    /**
     * Default constructor.
     *
     * @param service service class for entities
     */
    protected ContractRestController(AbstractService service) {
        super(service);
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
    @RequestMapping(value = "/contracts", method = RequestMethod.POST)
    public ResponseEntity<?> post(@Valid @RequestBody ApiContract input, BindingResult result) {
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/contracts/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
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

    /* /companies/types */
    /**@RequestMapping(value = "/contracts/types", method = RequestMethod.GET)
    public ResponseEntity<?> listAllTypes(Pageable pagination, CompanyFilter filter, BindingResult result) {
        return ...;
    }TODO*/
}
