package solvas.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.api.models.ApiModel;
import solvas.rest.query.InvoiceFilter;
import solvas.service.InvoiceService;
import solvas.service.models.Model;

import javax.validation.Valid;


/**
 * Rest controller for Invoice
 *
 */                                                                //TODO REPLACE
public class InvoiceRestController  extends AbstractRestController<Invoice,ApiInvoice> {

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
    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public ResponseEntity<?> listAll(Pageable pagination, InvoiceFilter filter, BindingResult result) {
        return super.listAll(pagination, filter, result);
    }


    @Override
    @RequestMapping(value = "/invoices/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        return super.getById(id);
    }



    /**
     * Get the active invoice for a company.
     *
     * @param id The ID of the company.
     * @param pagination The pagination.
     * @param filter The filters.
     * @param result The validation results.
     *
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{id}/invoices/current", method = RequestMethod.GET)
    public ResponseEntity<?> getActiveByCompanyId(@PathVariable int id,Pageable pagination, InvoiceFilter filter, BindingResult result) {
        //Todo filter set active
        filter.setFleet(id);
        return super.listAll(pagination,filter,result);
    }


    /**
     * Get invoice with id
     *
     * @param id The ID of the invoice.
     *
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{fleet_id}/invoices/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getByCompanyAndInvoiceId(@PathVariable int fleet_id, @PathVariable int id) {
        return super.getById(id);
    }

    /**
     * Get invoice with id
     *
     * @param id The ID of the invoice.
     *
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{fleet_id}/invoices/{id}{extension}", method = RequestMethod.GET)
    public ResponseEntity<?> getByCompanyAndInvoiceIdWithExtension(@PathVariable int fleet_id, @PathVariable int id,@PathVariable String extension) {
        //Todo process extension
        return super.getById(id);
    }


    /**
     * Get all invoices for a company.
     *
     * @param id The ID of the company.
     * @param pagination The pagination.
     * @param filter The filters.
     * @param result The validation results.
     *
     * @return The response.
     */
    @RequestMapping(value = "/fleets/{id}/invoices", method = RequestMethod.GET)
    public ResponseEntity<?> getByCompanyId(@PathVariable int id,Pageable pagination, InvoiceFilter filter, BindingResult result) {
        filter.setFleet(id);
        return super.listAll(pagination,filter,result);
    }


}