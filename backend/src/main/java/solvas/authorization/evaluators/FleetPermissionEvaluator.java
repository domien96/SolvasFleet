package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Fleet;

/**
 * Evaluate fleet-related permissions.
 */
public class FleetPermissionEvaluator extends AbstractPermissionEvaluator<Fleet> {
    {
        registerPermissionDecider("READ_INVOICES", this::canReadInvoices);
        registerPermissionDecider("READ_VEHICLES", this::canReadVehicles);
        registerPermissionDecider("MANAGE_VEHICLES", this::canManageVehicles);
    }

    /**
     * @param dao Autowired dao.
     */
    public FleetPermissionEvaluator(Dao<Fleet> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Fleet model) {
        return hasScope(authentication, "read:company:fleets", getCompanyId(model), "read:company:fleets");
    }

    /**
     * Check if the user can read vehicles of a fleet.
     *
     * @param authentication The authentication.
     * @param model The model.
     *
     * @return True if the user has permission.
     */
    public boolean canReadVehicles(Authentication authentication, Fleet model) {
        return hasScope(authentication, "read:company:fleets", getCompanyId(model), "read:companies:fleets");
    }

    /**
     * Check if the user can manage vehicles of a fleet.
     *
     * @param authentication The authentication.
     * @param model The fleet.
     *
     * @return True if the user has permission.
     */
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

    /**
     * Check if the user can read invoices.
     *
     * @param authentication The authentication.
     * @param model The fleet.
     *
     * @return True if the user has permission.
     */
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
