package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Fleet;

public class FleetPermissionEvaluator extends AbstractPermissionEvaluator<Fleet> {
    public FleetPermissionEvaluator(Dao<Fleet> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Fleet model) {
        return hasScope(authentication, "read:company:fleet", getCompanyId(model), "read:company:fleet");
    }

    private int getCompanyId(Fleet fleet) {
        return fleet.getCompany().getId();
    }

    @Override
    public boolean canCreate(Authentication authentication, Fleet model) {
        return false;
    }

    @Override
    public boolean canEdit(Authentication authentication, Fleet model) {
        return false;
    }

    @Override
    public boolean canDelete(Authentication authentication, Fleet model) {
        return false;
    }
}
