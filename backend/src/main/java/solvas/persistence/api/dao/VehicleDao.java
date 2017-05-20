package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.service.models.Vehicle;
import solvas.persistence.api.Dao;

import java.util.Optional;

/**
 * DAO for vehicles.
 *
 * @author Steven Bastiaens
 * @author Niko Strijbol
 */
@Repository
public interface VehicleDao extends Dao<Vehicle> {

    /**
     * Find a vehicle by it's VIN. This resulsts in maximal one response, since the database ensures uniqueness.
     *
     * @param vin The VIN to look for.
     *
     * @return The vehicle.
     */
    Optional<Vehicle> findByChassisNumber(String vin);
}