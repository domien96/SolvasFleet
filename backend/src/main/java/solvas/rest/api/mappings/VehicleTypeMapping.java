package solvas.rest.api.mappings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.models.VehicleType;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.fleetSubscription.FleetSubscriptionDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.user.UserDao;
import solvas.persistence.vehicle.VehicleDao;
import solvas.persistence.vehicleType.VehicleTypeDao;

/**
 * Created by steve on 11/03/2017.
 */
@Component
public class VehicleTypeMapping extends Mapping<VehicleType,String> {

    @Autowired
    public VehicleTypeMapping(RoleDao roleDao, CompanyDao companyDao, UserDao userDao, VehicleDao vehicleDao, VehicleTypeDao vehicleTypeDao, FleetSubscriptionDao fleetSubscriptionDao) {
        super(roleDao, companyDao, userDao, vehicleDao, vehicleTypeDao, fleetSubscriptionDao);
    }

    @Override
    public VehicleType convertToModel(String api) {
        return vehicleTypeDao.withType(api);
    }

    @Override
    public String convertToApiModel(VehicleType model) {
        return model.getName();
    }
}
