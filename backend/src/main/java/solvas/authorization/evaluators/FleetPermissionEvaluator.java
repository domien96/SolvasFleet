package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Fleet;

import static solvas.authorization.ApiPermissionStrings.*;

/**
 * Evaluate fleet-related permissions.
 */
public class FleetPermissionEvaluator extends AbstractPermissionEvaluator<Fleet> {

    {
        registerPermissionDecider("READ_INVOICES", this::canReadInvoices);
        registerPermissionDecider("READ_VEHICLES", this::canReadVehicles);
        registerPermissionDecider("MANAGE_VEHICLES", this::canManageVehicles);
        registerPermissionDecider("WRITE_INVOICES", this::canWriteInvoices);
    }

    /**
     * @param dao Autowired dao.
     */
    public FleetPermissionEvaluator(Dao<Fleet> dao) {
        super(dao);
    }

    /**
     * Check if a user can create invoices for a fleet
     *
     * @param authentication The authentication.
     * @param model The model.
     *
     * @return True if the user has permission.
     */
    public boolean canWriteInvoices(Authentication authentication, Fleet model) {
        return hasScope(authentication, WRITE_COMPANY_INVOICES, getCompanyId(model), WRITE_COMPANIES_INVOICES);
    }

    @Override
    public boolean canRead(Authentication authentication, Fleet model) {
        return hasScope(authentication, READ_COMPANY_FLEETS, getCompanyId(model), READ_COMPANY_FLEETS);
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
        return hasScope(authentication, READ_COMPANY_FLEETS, getCompanyId(model), READ_COMPANIES_FLEETS);
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
        return hasScope(authentication, WRITE_COMPANY_FLEETS, getCompanyId(model), WRITE_COMPANIES_FLEETS);
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
        return hasScope(authentication, READ_COMPANY, model.getCompany().getId(), READ_COMPANIES);
    }

    @Override
    public boolean canEdit(Authentication authentication, Fleet model) {
        return hasScope(authentication, WRITE_COMPANY_FLEETS, getCompanyId(model), WRITE_COMPANIES_FLEETS);

    }

    @Override
    public boolean canDelete(Authentication authentication, Fleet model) {
        return hasScope(authentication, WRITE_COMPANY_FLEETS, getCompanyId(model), WRITE_COMPANIES_FLEETS);
    }
}
