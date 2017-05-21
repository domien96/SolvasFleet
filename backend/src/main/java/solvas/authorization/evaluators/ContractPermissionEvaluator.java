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

    private int getCompanyId(Contract model) {
        if(model != null && model.getCompany()!= null) {
            return model.getCompany().getId();
        } else {
            return 0;
        }
    }
    @Override
    public boolean canRead(Authentication authentication, Contract model) {
        int companyId = getCompanyId(model);
        return hasScope(authentication, READ_COMPANY_CONTRACTS, companyId, READ_COMPANIES_CONTRACTS);
    }

    @Override
    public boolean canCreate(Authentication authentication, Contract model) {
        return hasScope(authentication, WRITE_COMPANY_CONTRACTS, getCompanyId(model), WRITE_COMPANIES_CONTRACTS);
    }

    @Override
    public boolean canEdit(Authentication authentication, Contract model) {
        return canCreate(authentication, model);
    }

    @Override
    public boolean canDelete(Authentication authentication, Contract model) {
        return canEdit(authentication, model);
    }
}
