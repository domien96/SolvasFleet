package solvas.authorization.evaluators;

import org.springframework.security.core.Authentication;
import solvas.persistence.api.Dao;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.SubFleet;
import solvas.service.models.Vehicle;

import java.util.stream.Stream;

public class VehiclePermissionEvaluator extends AbstractPermissionEvaluator<Vehicle> {
    public VehiclePermissionEvaluator(Dao<Vehicle> dao) {
        super(dao);
    }

    @Override
    public boolean canRead(Authentication authentication, Vehicle model) {
        return hasScope(authentication, "read:company:fleets")
                || getIds(model).anyMatch(id -> hasScope(authentication, "read:company:fleet")
        );
    }

    private Stream<Integer> getIds(Vehicle vehicle) {
         return Stream.concat(
                 vehicle.getFleetSubscriptions().stream()
                    .filter(FleetSubscription::isActive)
                    .map(FleetSubscription::getSubFleet)
                    .map(SubFleet::getFleet)
                    .map(Fleet::getId),
                 Stream.of(vehicle.getLeasingCompany().getId()));
    }

    @Override
    public boolean canCreate(Authentication authentication, Vehicle model) {
        return hasScope(authentication, "write:company:fleets")
                || getIds(model).anyMatch(id -> hasScope(authentication, "write:company:fleet")
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
