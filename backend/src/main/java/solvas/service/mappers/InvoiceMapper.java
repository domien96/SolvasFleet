package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiInvoice;
import solvas.rest.utils.SimpleUrlBuilder;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.mappers.exceptions.UnsupportedMappingException;
import solvas.service.models.Invoice;
import solvas.service.models.InvoiceType;

import java.math.BigDecimal;

/**
 * Mapper between for invoice
 */
@Component
public class InvoiceMapper extends AbstractMapper<Invoice,ApiInvoice> {

    private static final String ROOTPATH="fleets/{id}/invoices/";

    /**
     * Create a mapper between invoice and Apiinvoice
     *
     * @param daoContext The context for Dao's
     */
    public InvoiceMapper(DaoContext daoContext){
        super(daoContext,"paid","endDate","startDate");
    }

    @Override
    public Invoice convertToModel(ApiInvoice api) throws DependantEntityNotFound, EntityNotFoundException {
        throw new UnsupportedMappingException("Invoice should be generated automatically, not created by the user");
    }

    @Override
    public ApiInvoice convertToApiModel(Invoice model) {
        ApiInvoice api = new ApiInvoice();

        copyAttributes(api, model, "id", "createdAt", "updatedAt");
        copySharedAttributes(api, model);
        api.setTotalAmount(model.getTotalAmount().longValue());
        api.setType(model.getType().getText());
        api.setFleet(model.getFleet().getId());
        api.setUrl(SimpleUrlBuilder.buildUrlFromBase(ROOTPATH+"{invoice_id}",model.getFleet().getId(),model.getId()));
        return api;
    }
}