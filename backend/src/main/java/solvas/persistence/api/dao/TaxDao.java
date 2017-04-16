package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.Tax;

/**
 * DAO for taxes.
 *
 * @author Steven Bastiaens
 */
@Repository
public interface TaxDao extends Dao<Tax> {

}