package solvas.authorization.evaluators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import solvas.persistence.api.Dao;
import solvas.service.models.Company;

/**
 * Evaluate permissions related to companies.
 */
@Component
public class CompanyPermissionEvaluator extends AbstractPermissionEvaluator<Company> {
    {
        registerPermissionDecider("MANAGE_FLEETS", this::canManageFleets);
        registerPermissionDecider("LIST_FLEETS", this::canListFLeets);
    }

    /**
     * @param dao Autowired company dao.
     */
    @Autowired
    public CompanyPermissionEvaluator(Dao<Company> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Company model) {
        return hasScope(authentication, "read:company", model.getId(), "read:companies");
    }

    /**
     * Check if the user can edit fleets for a company.
     *
     * @param authentication The authentication.
     * @param model The company.
     *
     * @return True if the user can.
     *
     */
    public boolean canManageFleets(Authentication authentication, Company model) {
        return hasScope(authentication, "write:company:fleets", model.getId(), "write:companies:fleets");
    }

    /**
     * Check if the user can manage fleets for a company.
     *
     * @param authentication The authentication.
     * @param model The company.
     *
     * @return True if the user can.
     */
    public boolean canListFLeets(Authentication authentication, Company model) {
        return hasScope(authentication, "read:company:fleets", model.getId(), "read:companies:fleets");
    }

    @Override
    public boolean canCreate(Authentication authentication, Company model) {
        return hasScope(authentication, "create:company");
    }

    @Override
    public boolean canEdit(Authentication authentication, Company model) {
        return hasScope(authentication, "write:company", model.getId(), "write:companies");
    }

    @Override
    public boolean canDelete(Authentication authentication, Company model) {
        return hasScope(authentication, "archive:company", model.getId(), "archive:companies");
    }
}
