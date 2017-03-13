package solvas.persistence.api.dao;

import solvas.models.VehicleType;
import solvas.persistence.api.Dao;

import java.util.Collection;

public interface VehicleTypeDao extends Dao<VehicleType> {

    Collection<VehicleType> withType(String type);
}