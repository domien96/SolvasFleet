package solvas.persistence.api.dao;

import org.springframework.stereotype.Repository;
import solvas.persistence.api.Dao;
import solvas.service.models.VehicleType;

import java.util.Optional;

/**
 * Dao for vehicleTypes
 *
 * @author Steven Bastiaens
 * @author Niko Strijbol
 */
@Repository
public interface VehicleTypeDao extends Dao<VehicleType> {

    /**
     * Get the type from it's name.
     *
     * @param type The name of the type.
     *
     * @return The vehicle type.
     */
    Optional<VehicleType> findByName(String type);
}