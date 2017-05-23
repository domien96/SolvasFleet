package solvas.authorization.evaluators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import solvas.authorization.PermissionEvaluatorContext;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.VehicleDao;
import solvas.service.models.Company;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

import java.util.Optional;
import java.util.stream.Stream;

import static solvas.authorization.ApiPermissionStrings.*;

/**
 * Evaluate permissions for vehicles.
 */
@Component
public class VehiclePermissionEvaluator extends AbstractPermissionEvaluator<Vehicle> {

    {
        registerPermissionDecider("LIST_VEHICLES", this::canListAll);
        registerPermissionDecider("IMPORT_VEHICLES", this::canImport);
        registerPermissionDecider("DOWNLOAD_GREENCARD", this::canDownloadGreencard);
    }

    private boolean canDownloadGreencard(Authentication authentication, Vehicle vehicle) {
        Optional<Fleet> fleetOpt = vehicle.getFleetSubscriptions().stream().filter(FleetSubscription::isActive)
                .map(FleetSubscription::getFleet).findFirst();
        return fleetOpt.isPresent() &&
                hasScope(authentication, READ_COMPANY_GREENCARD, fleetOpt.get().getCompany().getId(), READ_COMPANIES_GREENCARD);
    }

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
        if (vehicle == null) {
            return Stream.empty();
        }
        return Stream.concat(
                vehicle.getFleetSubscriptions().stream()
                        .filter(FleetSubscription::isActive)
                        .map(FleetSubscription::getFleet)
                        .map(Fleet::getId),
                Stream.of(vehicle.getLeasingCompany().getId()));
    }

    /**
     * Check if a user can list all vehicles
     *
     * @param authentication The authentication.
     * @param model          The model. Unused
     * @return True if the user has permission.
     */
    public boolean canListAll(Authentication authentication, Vehicle model) {
        return hasScope(authentication, READ_COMPANIES_FLEETS);
    }

    @Override
    public boolean canCreate(Authentication authentication, Vehicle model) {
        return hasScope(authentication, WRITE_COMPANIES_FLEETS)
                || getIds(model).anyMatch(id -> hasScope(authentication, WRITE_COMPANY_FLEETS)
        );
    }

    public boolean canImport(Authentication authentication, Vehicle model) {
        return canCreate(authentication, model);
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
