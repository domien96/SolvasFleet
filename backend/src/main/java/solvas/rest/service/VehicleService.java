package solvas.rest.service;

import solvas.models.Vehicle;
import solvas.persistence.api.Dao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.models.ApiVehicle;

/**
 * VehicleService Class
 */
public class VehicleService extends AbstractService<Vehicle,ApiVehicle>{
    public VehicleService(Dao<Vehicle> modelDao, AbstractMapper<Vehicle, ApiVehicle> mapper) {
        super(modelDao, mapper);
    }
}
