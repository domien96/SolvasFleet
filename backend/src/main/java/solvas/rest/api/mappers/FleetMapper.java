package solvas.rest.api.mappers;

import solvas.models.Fleet;
import solvas.persistence.DaoContext;
import solvas.rest.api.models.ApiFleet;

/**
 * Created by David Vandorpe.
 */
public class FleetMapper extends AbstractMapper<Fleet,ApiFleet> {
    /**
     * Create a mapper between Fleet and ApiFleet
     *
     * @param daoContext Context for DAO's
     */
    public FleetMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Fleet convertToModel(ApiFleet api) {
        return null;
    }

    @Override
    public ApiFleet convertToApiModel(Fleet model) {
        return null;
    }
}
