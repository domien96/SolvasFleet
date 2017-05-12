package solvas.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import solvas.rest.api.models.ApiRevision;
import solvas.rest.query.AuditFilter;
import solvas.service.AuditService;
import solvas.service.models.Revision;

/**
 * Rest controller for Audit
 * Visit @ /audit
 */
@RestController
public class AuditRestController extends AbstractRestController<Revision,ApiRevision> {

    /**
     * Rest controller for Audit
     *
     * @param service service class for companies
     */
    @Autowired
    public AuditRestController(AuditService service) {
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
    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, AuditFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }


    @Override
    @RequestMapping(value = "/audit/{id}", method = RequestMethod.GET)
    //@PreAuthorize("hasPermission(#id, 'company', 'READ')") // TODO authorization
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

}