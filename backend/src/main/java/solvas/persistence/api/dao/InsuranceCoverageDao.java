package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.InsuranceCoverage;

/**
 * DAO for InsuranceCoverage.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface InsuranceCoverageDao extends Dao<InsuranceCoverage> {

}