package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.VehicleType;
import solvas.persistence.DaoContext;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleet.FleetDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.subFleet.SubFleetDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;

import java.util.Collection;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class VehicleTypeAbstractMapper extends AbstractMapper<VehicleType,String> {

    /**
     * TODO document
     *
     * @param daoContext
     */
    public VehicleTypeAbstractMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public VehicleType convertToModel(String api) {
        Collection <VehicleType> types = daoContext.getVehicleTypeDao().withType(api);
        VehicleType type=null;
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
