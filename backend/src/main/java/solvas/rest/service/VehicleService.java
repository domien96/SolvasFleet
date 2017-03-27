package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.Vehicle;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.VehicleDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.VehicleMapper;
import solvas.rest.api.models.ApiVehicle;

/**
 * VehicleService Class
 */
@Service
public class VehicleService extends AbstractService<Vehicle,ApiVehicle>{

    /**
     * Construct a VehicleService
     * @param context the DaoContext
     * @param mapper the mapper to map ApiVehicle and Vehicle
     */
    @Autowired
    public VehicleService(DaoContext context, VehicleMapper mapper) {
        super(context.getVehicleDao(), mapper);
    }
}
