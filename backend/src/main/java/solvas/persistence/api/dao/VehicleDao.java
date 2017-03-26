package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.models.Vehicle;
import solvas.persistence.api.Dao;

/**
 * DAO for vehicles.
 *
 * @author Steven Bastiaens
 * @author Niko Strijbol
 */
@Repository
public interface VehicleDao extends Dao<Vehicle> {

}