package solvas.rest.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.models.VehicleType;
import solvas.persistence.api.DaoContext;

import java.net.UnknownHostException;
import java.util.Collection;

/**
 * Mapper between VehicleType and it's String representation
 */
@Component
public class VehicleTypeAbstractMapper extends AbstractMapper<VehicleType,String> {

    /**
     * TODO document
     *
     * @param daoContext
     */
    @Autowired
    public VehicleTypeAbstractMapper(DaoContext daoContext) throws UnknownHostException {
        super(daoContext);
    }

    public VehicleTypeAbstractMapper(DaoContext daoContext,String urlroot) {
        super(daoContext,urlroot);
    }

    @Override
    public VehicleType convertToModel(String api) {
        Collection <VehicleType> types = daoContext.getVehicleTypeDao().withType(api);
        VehicleType type;
        if (types.size()==0){
            type = new VehicleType();
            type.setId(0);
            type.setName(api);
            return daoContext.getVehicleTypeDao().create(type);
        } else {
            return types.iterator().next();
        }
    }

    @Override
    public String convertToApiModel(VehicleType model) {
        return model.getName();
    }
}
