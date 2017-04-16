package solvas.authorization.resolvers;

import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.VehicleDao;
import solvas.service.models.Fleet;
import solvas.service.models.FleetSubscription;
import solvas.service.models.SubFleet;
import solvas.service.models.Vehicle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Resolve company id for vehicle resource
 */
public class VehicleToCompanyIdResolver implements CompanyIdResolver {
    private final VehicleDao vehicleDao;

    /**
     * Create instance
     * @param vehicleDao Dao used to query vehicles
     */
    public VehicleToCompanyIdResolver(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @Override
    public Collection<Integer> resolve(int targetId) throws EntityNotFoundException {
        return new ArrayList<Integer>() {{
            Vehicle vehicle = vehicleDao.find(targetId);
            addAll(vehicle.getFleetSubscriptions().stream()
                    .filter(FleetSubscription::isActive)
                    .map(FleetSubscription::getSubFleet)
                    .map(SubFleet::getFleet)
                    .map(Fleet::getId)
                    .collect(Collectors.toSet()));
            if (vehicle.getLeasingCompany() != null) {
                add(vehicle.getLeasingCompany().getId());
            }
        }};
    }
}
