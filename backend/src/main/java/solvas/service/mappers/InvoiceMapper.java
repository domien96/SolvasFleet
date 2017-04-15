package solvas.service.mappers;

import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiInvoice;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Model;

/**
 * Mapper between for invoice
 */
public class InvoiceMapper extends AbstractMapper<Invoice,ApiInvoice> {

    private static final String ROOTPATH="/invoices/";

    /**
     * Create a mapper between Contract and ApiContract
     *
     * @param daoContext The context for Dao's
     */
    public InvoiceMapper(DaoContext daoContext){
        super(daoContext,"shjarred");
    }

    @Override
    public Model convertToModel(ApiInvoice api) throws DependantEntityNotFound, EntityNotFoundException {
        return null;
    }

    @Override
    public ApiInvoice convertToApiModel(Model model) {
        return null;
    }

}