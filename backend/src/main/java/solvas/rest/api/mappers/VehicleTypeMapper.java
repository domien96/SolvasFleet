package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.VehicleType;
import solvas.persistence.api.DaoContext;

import java.util.Collection;

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
        Collection <VehicleType> types = daoContext.getVehicleTypeDao().findByName(api);
        VehicleType type;
        if (types.size()==0){
            type = new VehicleType();
            type.setId(0);
            type.setName(api);
            return daoContext.getVehicleTypeDao().save(type);
        } else {
            return types.iterator().next();
        }
    }


    @Override
    public String convertToApiModel(VehicleType model) {
        return model.getName();
    }
}
