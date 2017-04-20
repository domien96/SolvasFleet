package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.dao.VehicleTypeDao;
import solvas.service.models.InsuranceType;
import solvas.service.models.Vehicle;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.VehicleMapper;
import solvas.rest.api.models.ApiVehicle;
import solvas.service.models.VehicleType;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * VehicleService Class
 */
@Service
public class VehicleService extends AbstractService<Vehicle,ApiVehicle>{

    private final VehicleTypeDao vehicleTypesDao;

    /**
     * Construct a VehicleService
     * @param context the DaoContext
     * @param mapper the mapper to map ApiVehicle and Vehicle
     */
    @Autowired
    public VehicleService(DaoContext context, VehicleMapper mapper) {
        super(context.getVehicleDao(), mapper);
        this.vehicleTypesDao = context.getVehicleTypeDao();
    }

    /**
     * Finds all types of vehicles in the database
     * @return types of vehicles
     */
    public Collection<String> findAllVehicleTypes() {
        return vehicleTypesDao.findAll().stream().map(VehicleType::getName).collect(Collectors.toSet());

    }
}
