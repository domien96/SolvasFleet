package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.authorization.CompanyExtractor;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.query.CompanyInListFilter;
import solvas.rest.utils.PagedResult;
import solvas.service.CompanyService;
import solvas.service.exceptions.UnarchivableException;
import solvas.service.models.Company;

import javax.validation.Valid;
import java.util.Collection;


/**
 * Rest controller for Company
 * Visit @ /companies
 */
@RestController
public class CompanyRestController extends AbstractRestController<Company,ApiCompany> {

    private final CompanyExtractor companyExtractor;
    /**
     * Rest controller for Company
     *
     * @param service service class for companies
     */
    @Autowired
    public CompanyRestController(CompanyService service, CompanyExtractor companyExtractor) {
        super(service);
        this.companyExtractor = companyExtractor;
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
    @RequestMapping(value = "/companies", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, CompanyInListFilter filter, BindingResult result) {
        // If there are errors in the filtering, send bad request.
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Collection<Integer> companyIds = companyExtractor.extractCompanyIds(SecurityContextHolder.getContext().getAuthentication());
        PagedResult<ApiCompany> pagedResult;
        if(companyIds == null) {
            pagedResult = new PagedResult<>(service.findAll(pagination, filter));
        } else {
            filter.setIds(companyIds);
            pagedResult = new PagedResult<>(service.findAll(pagination, filter));
        }

        return new ResponseEntity<>(pagedResult, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/companies", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#input, 'CREATE')")
    public ResponseEntity<?> post(@Valid @RequestBody ApiCompany input, BindingResult result) {
        return super.post(input, result);
    }

    @Override
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'company', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }

    @Override
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasPermission(#id, 'company', 'DELETE')")
    public ResponseEntity<?> archiveById(@PathVariable int id) throws EntityNotFoundException, UnarchivableException {
        return super.archiveById(id);
    }

    @Override
    @RequestMapping(value = "/companies/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasPermission(#id, 'company', 'EDIT')")
    public ResponseEntity<?> put(@PathVariable int id, @Valid @RequestBody ApiCompany input,BindingResult result) {
        return super.put(id, input,result);
    }
}