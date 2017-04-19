package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.service.models.VehicleType;

/**
 * Mapper between VehicleType and it's String representation
 */
@Component
public class VehicleTypeMapper extends AbstractMapper<VehicleType,String> {

    /**
     * TODO document
     *
     * @param daoContext
     */
    public VehicleTypeMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public VehicleType convertToModel(String api) {
        VehicleType type = daoContext.getVehicleTypeDao().findByName(api);
        if (type == null) {
            type = new VehicleType();
            type.setId(0);
            type.setName(api);
            return daoContext.getVehicleTypeDao().save(type);
        } else {
            return type;
        }
    }


    @Override
    public String convertToApiModel(VehicleType model) {
        return model.getName();
    }
}
