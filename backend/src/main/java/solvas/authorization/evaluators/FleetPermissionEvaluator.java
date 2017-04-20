package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Fleet;

public class FleetPermissionEvaluator extends AbstractPermissionEvaluator<Fleet> {
    {
        registerPermissionDecider("READ_INVOICES", this::canReadInvoices);
        registerPermissionDecider("READ_VEHICLES", this::canReadVehicles);
        registerPermissionDecider("MANAGE_VEHICLES", this::canManageVehicles);
    }
    public FleetPermissionEvaluator(Dao<Fleet> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Fleet model) {
        return hasScope(authentication, "read:company:fleets", getCompanyId(model), "read:company:fleets");
    }

    public boolean canReadVehicles(Authentication authentication, Fleet model) {
        return hasScope(authentication, "read:company:fleets", getCompanyId(model), "read:companies:fleets");
    }

    public boolean canManageVehicles(Authentication authentication, Fleet model) {
        return hasScope(authentication, "write:company:fleets", getCompanyId(model), "write:companies:fleets");
    }

    private int getCompanyId(Fleet fleet) {
        return fleet.getCompany().getId();
    }

    @Override
    public boolean canCreate(Authentication authentication, Fleet model) {
        return false;
    }

    public boolean canReadInvoices(Authentication authentication, Fleet model) {
        return hasScope(authentication, "read:company", model.getCompany().getId(), "read:companies");
    }

    @Override
    public boolean canEdit(Authentication authentication, Fleet model) {
        return hasScope(authentication, "write:company:fleets", getCompanyId(model), "write:companies:fleets");

    }

    @Override
    public boolean canDelete(Authentication authentication, Fleet model) {
        return hasScope(authentication, "write:company:fleets", getCompanyId(model), "write:companies:fleets");
    }
}
