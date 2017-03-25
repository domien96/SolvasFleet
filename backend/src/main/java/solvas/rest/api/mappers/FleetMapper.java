package solvas.rest.api.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import solvas.models.Company;
import solvas.models.Fleet;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.SimpleUrlBuilder;
import solvas.rest.api.models.ApiFleet;

/**
 * @author Niko Strijbol
 */
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
        super(daoContext);
    }

    @Override
    public Fleet convertToModel(ApiFleet api) throws DependantEntityNotFound,EntityNotFoundException {
        Fleet fleet;
        if (api.getId() == 0) {
            fleet = new Fleet();
        } else {
            fleet = daoContext.getFleetDao().find(api.getId());
        }

        if (api.getName() != null) {
            fleet.setName(api.getName());
        }
        try {
            Company company = daoContext.getCompanyDao().find(api.getCompany());
            fleet.setCompany(company);
        } catch (EntityNotFoundException e) {
            throw new DependantEntityNotFound("Could not find company.", e);
        }

        return fleet;
    }

    @Override
    public ApiFleet convertToApiModel(Fleet model) {
        ApiFleet fleet = new ApiFleet();
        fleet.setId(model.getId());
        fleet.setCompany(model.getCompany() == null ? 0 : model.getCompany().getId());
        fleet.setName(model.getName());
        fleet.setCreatedAt(model.getCreatedAt());
        fleet.setUpdatedAt(model.getUpdatedAt());
        fleet.setLastUpdatedBy(0);
        fleet.setUrl(SimpleUrlBuilder.buildUrl(ROOTPATH + "{id}", model.getId()));
        return fleet;
    }
}