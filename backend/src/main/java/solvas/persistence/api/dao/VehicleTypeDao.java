package solvas.persistence.api.dao;

import solvas.models.VehicleType;
import solvas.persistence.api.Dao;

import java.util.Collection;

/**
 * Dao for vehicleTypes
 */
public interface VehicleTypeDao extends Dao<VehicleType> {
    Collection<VehicleType> withType(String type);
}