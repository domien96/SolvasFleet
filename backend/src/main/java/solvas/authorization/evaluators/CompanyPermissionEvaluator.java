package solvas.authorization.evaluators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import solvas.persistence.api.Dao;
import solvas.service.models.Company;

import static solvas.authorization.ApiPermissionStrings.*;

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
        return hasScope(authentication, READ_COMPANY, model.getId(), READ_COMPANIES);
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
        return hasScope(authentication, WRITE_COMPANY_FLEETS, model.getId(), WRITE_COMPANIES_FLEETS);
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
        return hasScope(authentication, READ_COMPANY_FLEETS, model.getId(), READ_COMPANIES_FLEETS);
    }

    @Override
    public boolean canCreate(Authentication authentication, Company model) {
        return hasScope(authentication, CREATE_COMPANY);
    }

    @Override
    public boolean canEdit(Authentication authentication, Company model) {
        return hasScope(authentication, WRITE_COMPANY, model.getId(), WRITE_COMPANIES);
    }

    @Override
    public boolean canDelete(Authentication authentication, Company model) {
        return hasScope(authentication, ARCHIVE_COMPANY, model.getId(), ARCHIVE_COMPANIES);
    }
}
