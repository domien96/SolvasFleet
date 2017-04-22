package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.query.InvoiceFilter;
import solvas.service.InvoiceService;
import solvas.service.models.Invoice;
import solvas.service.models.InvoiceType;


/**
 * Rest controller for Invoice
 */
@RestController
public class InvoiceRestController extends AbstractRestController<Invoice, ApiInvoice> {

    private InvoiceService invoiceService;

    /**
     * Default constructor.
     *
     * @param service service class for entities
     */
    @Autowired
    public InvoiceRestController(InvoiceService service) {
        super(service);
        invoiceService = service;
    }

    /**
     * Query all models, accounting for pagination settings and respect the filters. The return value of this
     * method will contain an object, according to the API spec.
     *
     * @param pagination The pagination information.
     * @param filter     The filters.
     * @param result     The validation results of the filterResult
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
     * @param id The ID of the fleet
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{id}/invoices/current", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'fleet', 'READ_INVOICES')")
    public ResponseEntity<?> getActiveByFleetId(@PathVariable int id, @RequestParam("type") String type) throws EntityNotFoundException {
        InvoiceType invtype = InvoiceType.fromString(type);
        if(invtype == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(invoiceService.findActiveInvoiceByType(id,invtype), HttpStatus.OK);
    }

     /**
     * Get the active invoice for a fleet.
     * @param id The ID of the fleet
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{fleetId}/invoices/current.pdf", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'fleet', 'READ')")
    public ModelAndView getpdfcurrent(@PathVariable int id, @RequestParam("type") String type) throws EntityNotFoundException {
        InvoiceType invtype = InvoiceType.fromString(type);
        if(invtype == null) {
            return null;
        }
        ApiInvoice a = invoiceService.findActiveInvoiceByType(id,invtype);
        return new ModelAndView("InvoicePdfView", "invoice", a);
    }

    /**
     * Get invoice with id
     *
     * @param id      The ID of the invoice.
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{fleetId}/invoices/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'invoice', 'READ')")
    public ResponseEntity<?> getByFleetAndInvoiceId(@PathVariable int id) {
        return super.getById(id);
    }

    /**
     * Get invoice with id
     *
     * @param fleetId   the id of the fleet
     * @param id        The ID of the invoice.
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{fleetId}/invoices/{id}.pdf", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'invoice', 'READ')")
    public ModelAndView getByFleetAndInvoiceIdWithExtension(@PathVariable int fleetId, @PathVariable int id) throws EntityNotFoundException {
        ApiInvoice invoice = service.getById(id);
        return new ModelAndView("InvoicePdfView", "invoice", invoice);
    }


    /**
     * Get all invoices for a fleet
     *
     * @param id The ID of the fleet.
     * @param pagination The pagination.
     * @param filter     The filters.
     * @param result     The validation results.
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{id}/invoices", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'fleet', 'READ_INVOICES')")
    public ResponseEntity<?> getByFleetId(@PathVariable int id, Pageable pagination, InvoiceFilter filter, BindingResult result) throws EntityNotFoundException {
        filter.setFleet(id);
        return super.listAll(pagination, filter, result);
    }
}