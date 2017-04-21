package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Invoice;
import solvas.service.models.InvoiceType;
import solvas.service.models.Model;

import java.math.BigDecimal;

/**
 * Mapper between for invoice
 */
@Component
public class InvoiceMapper extends AbstractMapper<Invoice,ApiInvoice> {

    private static final String ROOTPATH="/invoices/";

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
        Invoice invoice = api.getId()==0? new Invoice():daoContext.getInvoiceDao().find(api.getId());
        copySharedAttributes(invoice, api);
        invoice.setAmount(BigDecimal.valueOf(api.getTotalAmount()));
        invoice.setType(InvoiceType.fromString(api.getType()));
        invoice.setFleet(daoContext.getFleetDao().find(api.getFleet()));
        return invoice;
    }

    @Override
    public ApiInvoice convertToApiModel(Invoice model) {
        ApiInvoice api = new ApiInvoice();

        copyAttributes(api, model, "id", "createdAt", "updatedAt");
        copySharedAttributes(api, model);
        api.setTotalAmount(model.getAmount().longValue());
        api.setType(model.getType().getText());
        api.setFleet(model.getFleet().getId());
        api.setUrl(ROOTPATH);
        return api;
    }

}