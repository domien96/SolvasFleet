package solvas.service.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.service.models.Fleet;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.mappers.exceptions.FieldNotFoundException;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.SimpleUrlBuilder;
import solvas.rest.api.models.ApiFleet;

@Component
public class FleetMapper extends AbstractMapper<Fleet, ApiFleet> {

    private static final String ROOTPATH = "/fleets/";

    /**
     * Map fleets.
     *
     * @param daoContext autowired
     */
    @Autowired
    public FleetMapper(DaoContext daoContext) {
        super(daoContext, "name");
    }

    @Override
    public Fleet convertToModel(ApiFleet api) throws DependantEntityNotFound
                ,EntityNotFoundException,FieldNotFoundException {
        final Fleet fleet = api.getId()==0?new Fleet():daoContext.getFleetDao().find(api.getId());
        copySharedAttributes(fleet, api);
        try {
            fleet.setCompany(daoContext.getCompanyDao().find(api.getCompany()));
        }
        catch (EntityNotFoundException e)
        {
            throw new DependantEntityNotFound("Can't find company",e);
        }
        return fleet;
    }

    @Override
    public ApiFleet convertToApiModel(Fleet model) throws FieldNotFoundException {
        ApiFleet fleet = new ApiFleet();

        copySharedAttributes(fleet, model);
        copyAttributes(fleet, model, "createdAt", "updatedAt", "id");

        fleet.setCompany(model.getCompany() == null ? 0 : model.getCompany().getId());
        fleet.setUrl(SimpleUrlBuilder.buildUrlFromBase(ROOTPATH + "{id}", model.getId()));
        return fleet;
    }
}