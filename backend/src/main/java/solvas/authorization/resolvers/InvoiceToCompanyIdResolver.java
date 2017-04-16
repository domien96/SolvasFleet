package solvas.authorization.resolvers;

import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.InvoiceDao;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Resolve company id for invoice resource
 */
public class InvoiceToCompanyIdResolver implements CompanyIdResolver {
    private final InvoiceDao invoiceDao;

    /**
     * Create instance
     * @param invoiceDao Dao used to query invoices
     */
    public InvoiceToCompanyIdResolver(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Override
    public Collection<Integer> resolve(int targetId) throws EntityNotFoundException {
        return new ArrayList<Integer>() {{
            add(invoiceDao.find(targetId).getFleet().getCompany().getId());
        }};
    }
}
