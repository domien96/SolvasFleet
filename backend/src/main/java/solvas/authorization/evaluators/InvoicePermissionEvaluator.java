package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Invoice;

public class InvoicePermissionEvaluator extends AbstractPermissionEvaluator<Invoice> {
    public InvoicePermissionEvaluator(Dao<Invoice> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Invoice model) {
        return hasScope(authentication, "read:company", model.getFleet().getCompany().getId(), "read:companies");
    }

    @Override
    public boolean canCreate(Authentication authentication, Invoice model) {
        return false;
    }

    @Override
    public boolean canEdit(Authentication authentication, Invoice model) {
        return false;
    }

    @Override
    public boolean canDelete(Authentication authentication, Invoice model) {
        return false;
    }
}
