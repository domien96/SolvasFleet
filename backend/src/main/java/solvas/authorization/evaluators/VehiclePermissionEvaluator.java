package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import java.util.stream.Stream;

import static solvas.authorization.ApiPermissionStrings.*;

/**
 * Evaluate permissions for vehicles.
 */
public class VehiclePermissionEvaluator extends AbstractPermissionEvaluator<Vehicle> {

    /**
     * @param dao Autowired dao.
     */
    public VehiclePermissionEvaluator(Dao<Vehicle> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Vehicle model) {
        return hasScope(authentication, READ_COMPANIES_FLEETS)
                || getIds(model).anyMatch(id -> hasScope(authentication, READ_COMPANY_FLEETS)
        );
    }

    private Stream<Integer> getIds(Vehicle vehicle) {
         return Stream.concat(
                 vehicle.getFleetSubscriptions().stream()
                    .filter(FleetSubscription::isActive)
                    .map(FleetSubscription::getFleet)
                    .map(Fleet::getId),
                 Stream.of(vehicle.getLeasingCompany().getId()));
    }

    @Override
    public boolean canCreate(Authentication authentication, Vehicle model) {
        return hasScope(authentication, WRITE_COMPANIES_FLEETS)
                || getIds(model).anyMatch(id -> hasScope(authentication, WRITE_COMPANY_FLEETS)
        );
    }

    @Override
    public boolean canEdit(Authentication authentication, Vehicle model) {
        return canCreate(authentication, model);
    }

    @Override
    public boolean canDelete(Authentication authentication, Vehicle model) {
        return canEdit(authentication, model);
    }
}
