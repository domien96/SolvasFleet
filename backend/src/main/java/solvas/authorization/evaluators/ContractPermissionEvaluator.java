package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Contract;

import static solvas.authorization.ApiPermissionStrings.*;

/**
 * Evaluate contract-related permissions.
 */
public class ContractPermissionEvaluator extends AbstractPermissionEvaluator<Contract> {

    /**
     * @param dao Autowired dao.
     */
    public ContractPermissionEvaluator(Dao<Contract> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Contract model) {
        return hasScope(authentication, READ_COMPANY_CONTRACTS, model.getCompany().getId(), READ_COMPANIES_CONTRACTS);
    }

    @Override
    public boolean canCreate(Authentication authentication, Contract model) {
        return hasScope(authentication, WRITE_COMPANY_CONTRACTS, model.getCompany().getId(), WRITE_COMPANIES_CONTRACTS);
    }

    @Override
    public boolean canEdit(Authentication authentication, Contract model) {
        return hasScope(authentication, WRITE_COMPANY_CONTRACTS, model.getCompany().getId(), WRITE_COMPANIES_CONTRACTS);
    }

    @Override
    public boolean canDelete(Authentication authentication, Contract model) {
        return hasScope(authentication, WRITE_COMPANY_CONTRACTS, model.getCompany().getId(), WRITE_COMPANIES_CONTRACTS);
    }
}
