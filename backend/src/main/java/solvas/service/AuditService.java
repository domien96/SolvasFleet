package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.rest.api.models.ApiRevision;
import solvas.service.mappers.AuditMapper;
import solvas.persistence.api.DaoContext;
import solvas.service.models.Revision;

/**
 * AuditService class
 */
@Service
public class AuditService extends AbstractService<Revision,ApiRevision> {

    /**
     * Construct a AuditService
     * @param context the daocontext
     * @param mapper the mapper to map ApiRevision and Revision
     */
    @Autowired
    public AuditService(DaoContext context, AuditMapper mapper) {
        super(context.getRevisionDao(), mapper);
    }
}
