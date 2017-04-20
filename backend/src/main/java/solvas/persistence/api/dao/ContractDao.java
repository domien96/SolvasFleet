package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Company;
import solvas.service.models.Contract;
import solvas.service.models.Fleet;

import java.util.Collection;

/**
 * DAO for Contract.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface ContractDao extends Dao<Contract> {
    /**
     * Find all contracts with given company
     *
     * @param company the company of which the contracts are needed
     * @return Contracts with given company
     */
    Collection<Contract> findByCompany(Company company);
}