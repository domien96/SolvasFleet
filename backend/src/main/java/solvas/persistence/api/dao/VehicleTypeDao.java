package solvas.persistence.api.dao;

import solvas.models.VehicleType;
import solvas.persistence.api.Dao;

import java.util.Collection;

/**
 * Dao for vehicleTypes
 */
public interface VehicleTypeDao extends Dao<VehicleType> {

    /**
     * Get all models with a given type.
     *
     * TODO: we should implement constraints in the database to prevent two
     * models with the same type, so an uniqueness constraint should be applied.
     * Once that is done, this method can return one type instead of a collection.
     *
     * @param type The name of the type.
     *
     * @return Should be a collection with one type.
     */
    Collection<VehicleType> findByName(String type);
}