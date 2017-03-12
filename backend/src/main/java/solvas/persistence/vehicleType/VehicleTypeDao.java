package solvas.persistence.vehicleType;

import solvas.models.VehicleType;
import solvas.persistence.Dao;

import java.util.Collection;

public interface VehicleTypeDao extends Dao<VehicleType> {

    Collection<VehicleType> withType(String type);
}