package solvas.authorization.resolvers;

import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FleetDao;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Resolve company id for fleet resource
 */
public class FleetToCompanyIdResolver implements CompanyIdResolver {
    private final FleetDao fleetDao;

    /**
     * Create instance
     * @param fleetDao Dao used to query fleets
     */
    public FleetToCompanyIdResolver(FleetDao fleetDao) {
        this.fleetDao = fleetDao;
    }

    @Override
    public Collection<Integer> resolve(int targetId) throws EntityNotFoundException {
        return new ArrayList<Integer>() {{
            add(fleetDao.find(targetId).getCompany().getId());
        }};
    }
}
