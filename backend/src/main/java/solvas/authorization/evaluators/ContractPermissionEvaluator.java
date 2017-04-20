package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Contract;

public class ContractPermissionEvaluator extends AbstractPermissionEvaluator<Contract> {
    public ContractPermissionEvaluator(Dao<Contract> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Contract model) {
        return hasScope(authentication, "read:company:contracts", model.getCompany().getId(), "read:companies:contracts");
    }

    @Override
    public boolean canCreate(Authentication authentication, Contract model) {
        return hasScope(authentication, "write:company:contracts", model.getCompany().getId(), "write:companies:contracts");
    }

    @Override
    public boolean canEdit(Authentication authentication, Contract model) {
        return hasScope(authentication, "write:company:contracts", model.getCompany().getId(), "write:companies:contracts");
    }

    @Override
    public boolean canDelete(Authentication authentication, Contract model) {
        return hasScope(authentication, "write:company:contracts", model.getCompany().getId(), "write:companies:contracts");
    }
}
