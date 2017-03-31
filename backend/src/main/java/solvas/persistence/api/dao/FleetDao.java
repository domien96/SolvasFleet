package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.service.models.Fleet;
import solvas.persistence.api.Dao;

/**
 * DAO for a fleet.
 *
 * @author Niko Strijbol
 */
@Repository
public interface FleetDao extends Dao<Fleet> {

}