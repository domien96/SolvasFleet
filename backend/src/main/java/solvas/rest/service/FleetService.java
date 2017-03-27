package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.Fleet;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.FleetMapper;
import solvas.rest.api.models.ApiFleet;

/**
 * Fleetservice class
 */
@Service
public class FleetService extends AbstractService<Fleet,ApiFleet> {


    /**
     * Construct a FleetService
     * @param context the DaoContext
     * @param mapper the mapper to map ApiFleet and Fleet
     */
    @Autowired
    public FleetService(DaoContext context, FleetMapper mapper) {
        super(context.getFleetDao(), mapper);
    }
}
