package solvas.rest.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import solvas.models.Company;
import solvas.models.Fleet;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.exceptions.DependantEntityNotFound;
import solvas.rest.api.mappers.exceptions.FieldNotFoundException;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiCompany;
import solvas.rest.api.models.ApiFleet;

/**
 * @author Niko Strijbol
 */
@Component
public class FleetMapper extends AbstractMapper<Fleet, ApiFleet> {

    private static final String ROOT = "/fleets/";

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
        fleet.setLastUpdatedBy(0);
        fleet.setUrl(ROOT + model.getId());
        return fleet;
    }
}