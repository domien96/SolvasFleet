package solvas.rest.api.mappers;

import org.springframework.stereotype.Component;
import solvas.models.VehicleType;
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
     * @param roleDao
     * @param companyDao
     * @param userDao
     * @param vehicleDao
     * @param vehicleTypeDao
     * @param fleetSubscriptionDao
     * @param fleetDao
     * @param subFleetDao
     */
    public VehicleTypeAbstractMapper(RoleDao roleDao, CompanyDao companyDao, UserDao userDao, VehicleDao vehicleDao
            , VehicleTypeDao vehicleTypeDao, FleetSubscriptionDao fleetSubscriptionDao, FleetDao fleetDao, SubFleetDao subFleetDao) {
        super(roleDao, companyDao, userDao, vehicleDao, vehicleTypeDao, fleetSubscriptionDao, fleetDao, subFleetDao);
    }

    @Override
    public VehicleType convertToModel(String api) {
        Collection <VehicleType> types = vehicleTypeDao.withType(api);
        VehicleType type=null;
        if (types.size()==0){
            type = new VehicleType();
            type.setId(0);
            type.setName(api);
            return vehicleTypeDao.save(type);
        } else {
            return types.iterator().next();
        }

    }

    @Override
    public String convertToApiModel(VehicleType model) {
        return model.getName();
    }
}
