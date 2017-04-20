package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.api.models.ApiModel;
import solvas.rest.query.InvoiceFilter;
import solvas.service.InvoiceService;
import solvas.service.models.Invoice;
import solvas.service.models.Model;

import javax.validation.Valid;


/**
 * Rest controller for Invoice
 *
 */
@RestController
public class InvoiceRestController extends AbstractRestController<Invoice,ApiInvoice> {

    /**
     * Default constructor.
     *
     * @param service service class for entities
     */
    @Autowired
    public InvoiceRestController(InvoiceService service) {
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
    @PreAuthorize("hasPermission(0, 'invoice', 'READ')")
    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, InvoiceFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }


    @Override
    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'invoice', 'READ')")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }



    /**
     * Get the active invoice for a fleet.
     *
     * @param fleetId The ID of the fleet
     * @param pagination The pagination.
     * @param filter The filters.
     * @param result The validation results.
     *
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{fleetId}/invoices/current", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#fleetId, 'fleet', 'READ_INVOICES')")
    public ResponseEntity<?> getActiveByCompanyId(@PathVariable int fleetId,Pageable pagination, InvoiceFilter filter, BindingResult result) {
        //Todo filter set active
        filter.setFleet(fleetId);
        return super.listAll(pagination,filter,result);
    }


    /**
     * Get invoice with id
     *
     * @param id The ID of the invoice.
     *
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{fleetId}/invoices/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'invoice', 'READ')")
    public ResponseEntity<?> getByCompanyAndInvoiceId(@PathVariable int id) {
        return super.getById(id);
    }

    /**
     * Get invoice with id
     *
     * @param id The ID of the invoice.
     * @param fleetId the id of the fleet
     * @param extension the extension of the desired return document
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{fleetId}/invoices/{id}{extension}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'invoice', 'READ')")
    public ResponseEntity<?> getByCompanyAndInvoiceIdWithExtension(@PathVariable int fleetId, @PathVariable int id,@PathVariable String extension) {
        //Todo process extension
        return super.getById(id);
    }


    /**
     * Get all invoices for a fleet
     *
     * @param id The ID of the fleet.
     * @param pagination The pagination.
     * @param filter The filters.
     * @param result The validation results.
     *
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{id}/invoices", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'fleet', 'READ_INVOICES')")
    public ResponseEntity<?> getByFleetId(@PathVariable int id,Pageable pagination, InvoiceFilter filter, BindingResult result) {
        filter.setFleet(id);
        return super.listAll(pagination,filter,result);
    }
}