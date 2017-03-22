package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.Fleet;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FleetDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.DependantEntityNotFound;
import solvas.rest.api.mappers.FleetMapper;
import solvas.rest.api.models.ApiFleet;

/**
 * Fleetservice class
 */
@Service
public class FleetService extends AbstractService<Fleet,ApiFleet> {
    @Autowired
    private DaoContext context;

    @Autowired
    public FleetService(DaoContext context, FleetMapper mapper) {
        super(context.getFleetDao(), mapper);
    }

    @Override
    public ApiFleet update(int id,ApiFleet input)
    {
        input.setId(id);
        Fleet fleet=mapper.convertToModel(input,modelDao.find(id));
        try {
            fleet.setCompany(context.getCompanyDao().find(input.getCompany()));
        } catch (EntityNotFoundException e) {
            throw new DependantEntityNotFound("Could not find company.", e);
        }
        return mapper.convertToApiModel(modelDao.update(fleet));
    }
}
