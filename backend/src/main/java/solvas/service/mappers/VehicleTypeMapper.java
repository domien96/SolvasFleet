package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.models.VehicleType;

import java.util.Optional;

/**
 * Mapper between VehicleType and it's String representation
 */
@Component
public class VehicleTypeMapper extends AbstractMapper<VehicleType,String> {

    /**
     * Create a mapper between Vehicle type and vehicle type in the api as a string
     *
     * @param daoContext The context for Dao's
     */
    public VehicleTypeMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public VehicleType convertToModel(String api) throws EntityNotFoundException {
        Optional<VehicleType> type = daoContext.getVehicleTypeDao().findByName(api);
        if (type.isPresent()) {
            return type.get();
        } else {
            throw new EntityNotFoundException("Could not find vehicle type");
        }
    }


    @Override
    public String convertToApiModel(VehicleType model) {
        return model.getName();
    }
}
