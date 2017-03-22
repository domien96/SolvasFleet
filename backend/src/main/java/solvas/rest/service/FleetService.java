package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.Fleet;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.FleetDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.FleetMapper;
import solvas.rest.api.models.ApiFleet;

/**
 * Fleetservice class
 */
@Service
public class FleetService extends AbstractService<Fleet,ApiFleet> {
    @Autowired
    public FleetService(FleetDao modelDao, FleetMapper mapper) {
        super(modelDao, mapper);
    }
}
