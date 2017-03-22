package solvas.rest.service;

import solvas.models.Fleet;
import solvas.persistence.api.Dao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.models.ApiFleet;

/**
 * Fleetservice class
 */
public class FleetService extends AbstractService<Fleet,ApiFleet> {
    public FleetService(Dao<Fleet> modelDao, AbstractMapper<Fleet, ApiFleet> mapper) {
        super(modelDao, mapper);
    }
}
