package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Insurance;

/**
 * DAO for Insurance.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface InsuranceDao extends Dao<Insurance> {

}