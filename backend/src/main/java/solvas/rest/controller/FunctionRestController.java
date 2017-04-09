package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import solvas.rest.api.models.ApiFunction;
import solvas.rest.query.FunctionFilter;
import solvas.service.AbstractService;
import solvas.service.models.Function;
import org.springframework.data.domain.Pageable;


@RestController
public class FunctionRestController extends AbstractRestController<Function, ApiFunction> {

    /**
     * Default constructor.
     *
     * @param service service class for entities
     */
    @Autowired
    protected FunctionRestController(AbstractService<Function, ApiFunction> service) {
        super(service);
    }

    @RequestMapping(value = "/users/{userId}/functions", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(
            Pageable pagination,
            FunctionFilter filter,
            BindingResult result,
            @PathVariable  int userId) {
        filter.setUser(userId);
        return super.listAll(pagination, filter, result);
    }
}
