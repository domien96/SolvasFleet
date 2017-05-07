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
import solvas.persistence.api.dao.InvoiceDao;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.invoices.InvoiceFileViewResolver;
import solvas.rest.invoices.pdf.InvoicePdfView;
import solvas.rest.query.InvoiceFilter;
import solvas.service.InvoiceService;
import solvas.service.models.Invoice;
import solvas.service.models.InvoiceType;

import java.util.HashMap;


/**
 * Rest controller for Invoice
 */
@RestController
public class InvoiceRestController extends AbstractRestController<Invoice, ApiInvoice> {

    private InvoiceService invoiceService;
    private InvoiceDao invoiceDao;

    /**
     * Default constructor.
     *
     * @param service    service class for entities
     * @param invoiceDao The dao for invoices.
     */
    @Autowired
    public InvoiceRestController(InvoiceService service, InvoiceDao invoiceDao) {
        super(service);
        invoiceService = service;
        this.invoiceDao = invoiceDao;
    }

    /**
     * TODO: this is not part of the API yet.
     * <p>
     * Generate a PDF version of the current invoice.
     *
     * @param id The ID of the fleet.
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{id}/invoices/current.pdf", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'fleet', 'READ_INVOICES')")
    public Object getCurrentPdfByFleetId(@PathVariable int id) throws EntityNotFoundException {
        return new ModelAndView(InvoiceFileViewResolver.BILLING_INVOICE_PDF_VIEW, InvoicePdfView.MODEL_NAME, invoiceService.findCurrentInvoice(id));
    }

    @RequestMapping(value = "/fleets/{fleetId}/invoices/correct", method = RequestMethod.POST)
    @PreAuthorize("hasPermission(#fleetId, 'fleet', 'WRITE_INVOICES')")
    public ResponseEntity<?> getCorrectInvoiceByFleetId(@PathVariable int fleetId) throws EntityNotFoundException {
        boolean corrected = invoiceService.generateCorrectionsFor(fleetId);
        return new ResponseEntity<>(new HashMap<String, Object>() {{
            put("corrections", corrected);
        }}, HttpStatus.OK);
    }

    /**
     * Get the active invoice for a fleet.
     *
     * @param id   The ID of the fleet
     * @param type The type of invoice.
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{id}/invoices/current", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'fleet', 'READ_INVOICES')")
    public ResponseEntity<?> getActiveByFleetId(@PathVariable int id, @RequestParam("type") String type) throws EntityNotFoundException {
        InvoiceType invtype = InvoiceType.fromString(type);
        if (invtype == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(invoiceService.findActiveInvoiceByType(id, invtype), HttpStatus.OK);
    }

    /**
     * Get invoice with id
     *
     * @param id The ID of the invoice.
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
     * @param fleetId the id of the fleet
     * @param id      The ID of the invoice.
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{fleetId}/invoices/{id}.pdf", method = RequestMethod.GET)
    @PreAuthorize("hasPermission(#id, 'invoice', 'READ')")
    public ModelAndView getByFleetAndInvoiceIdWithExtension(@PathVariable int fleetId, @PathVariable int id) throws EntityNotFoundException {
        Invoice invoice = invoiceDao.find(id);
        return new ModelAndView(InvoiceFileViewResolver.getViewName(invoice), InvoicePdfView.MODEL_NAME, invoiceService.generateInvoiceForView(invoice));
    }


    /**
     * Get all invoices for a fleet
     *
     * @param id         The ID of the fleet.
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