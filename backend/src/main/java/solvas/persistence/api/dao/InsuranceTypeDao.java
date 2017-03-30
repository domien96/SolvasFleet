package solvas.persistence.api.dao;


import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.InsuranceType;

/**
 * DAO for InsuranceType.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface InsuranceTypeDao extends Dao<InsuranceType> {

}