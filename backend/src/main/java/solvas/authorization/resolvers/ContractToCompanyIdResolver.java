package solvas.authorization.resolvers;

import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.ContractDao;
import solvas.service.models.Contract;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Resolve company id for contract resource
 */
public class ContractToCompanyIdResolver implements CompanyIdResolver {
    private final ContractDao contractDao;

    /**
     * Create instance
     * @param contractDao Dao to query contracts
     */
    public ContractToCompanyIdResolver(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    @Override
    public Collection<Integer> resolve(int targetId) throws EntityNotFoundException {
        return new ArrayList<Integer>() {{
            Contract contract = contractDao.find(targetId);
            add(contract.getCompany().getId());
            add(contract.getFleetSubscription().getSubFleet().getFleet().getCompany().getId());
        }};
    }
}
