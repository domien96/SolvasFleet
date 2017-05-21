package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Invoice;

import static solvas.authorization.ApiPermissionStrings.*;
import static solvas.authorization.ApiPermissionStrings.WRITE_COMPANIES_INVOICES;

/**
 * Evaluate permissions related to invoices.
 */
public class InvoicePermissionEvaluator extends AbstractPermissionEvaluator<Invoice> {

    /**
     * @param dao Autowired dao.
     */
    public InvoicePermissionEvaluator(Dao<Invoice> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Invoice model) {
        return hasScope(authentication, READ_COMPANY, model.getFleet().getCompany().getId(), READ_COMPANIES);
    }

    @Override
    public boolean canCreate(Authentication authentication, Invoice model) {
        return false;
    }

    @Override
    public boolean canEdit(Authentication authentication, Invoice model) {
        return hasScope(authentication, WRITE_COMPANY_INVOICES, getCompanyId(model), WRITE_COMPANIES_INVOICES);
    }

    private int getCompanyId(Invoice invoice) {
        return invoice.getFleet().getCompany().getId();
    }

    @Override
    public boolean canDelete(Authentication authentication, Invoice model) {
        return false;
    }
}
