package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Contract;

/**
 * DAO for Contract.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface ContractDao extends Dao<Contract> {

}