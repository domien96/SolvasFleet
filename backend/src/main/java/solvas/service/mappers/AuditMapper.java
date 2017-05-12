package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiRevision;
import solvas.rest.utils.SimpleUrlBuilder;
import solvas.service.models.Revision;

/**
 * Class to map {@link Revision} in the persistence layer to {@link ApiRevision} in the API layer
 */
@Component
public class AuditMapper extends AbstractMapper<Revision,ApiRevision> {

    private static final String ROOTPATH ="/audit/";

    /**
     * Create a mapper between Revision  and ApiRevision
     *
     * @param daoContext The context for Dao's
     */
    public AuditMapper(DaoContext daoContext) {
        super(daoContext, "entity", "entityType", "logDate","payload");
    }

    @Override
    public Revision convertToModel(ApiRevision ignored)  {
        throw new RuntimeException("AuditMapper.convertToModel should never be called");
    }


    @Override
    public ApiRevision convertToApiModel(Revision revision) {
        ApiRevision api = new ApiRevision();

        copyAttributes(api, revision, "id", "createdAt", "updatedAt");
        copySharedAttributes(api, revision);
        api.setUser(revision.getUser().getId());
        api.setMethod(revision.getMethod().getText());
        api.setUrl(SimpleUrlBuilder.buildUrlFromBase(ROOTPATH + "{id}", api.getId()));

        return api;
    }
}
