package solvas.authorization.evaluators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import solvas.persistence.api.Dao;
import solvas.rest.api.models.ApiCompany;
import solvas.service.models.Company;

@Component
public class CompanyPermissionEvaluator extends AbstractPermissionEvaluator<Company> {
    {
        registerPermissionDecider("CREATE_FLEET", this::canCreateFleet);
    }
    @Autowired
    public CompanyPermissionEvaluator(Dao<Company> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Company model) {
        return hasScope(authentication, "read:company", model.getId(), "read:companies");
    }

    public boolean canCreateFleet(Authentication authentication, Company model) {
        return hasScope(authentication, "read:company:fleet", model.getId(), "read:company:fleets");
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
