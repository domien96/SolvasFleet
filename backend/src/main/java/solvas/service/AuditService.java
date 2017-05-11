package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.rest.api.models.ApiRevision;
import solvas.service.mappers.AuditMapper;
import solvas.persistence.api.DaoContext;
import solvas.service.models.Revision;

/**
 * CompanyService class
 */
@Service
public class AuditService extends AbstractService<Revision,ApiRevision> {

    /**
     * Construct a CcmpanyService
     * @param context the daocontext
     * @param mapper the mapper to map ApiCompany and Company
     */
    @Autowired
    public AuditService(DaoContext context, AuditMapper mapper) {
        super(context.getRevisionDao(), mapper);
    }
}
