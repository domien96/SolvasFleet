package filters;


import solvas.persistence.api.Filter;
import solvas.rest.query.AuditFilter;
import solvas.service.models.Revision;

public class AuditFilterTest extends AbstractFilterTest<Revision> {
    @Override
    Filter<Revision> getFilterWithCorrectParameters() {
        AuditFilter auditFilter = new AuditFilter();
        auditFilter.setAfter("2016-02-14T18:32:04");
        auditFilter.setBefore("2016-02-14T18:32:04");
        auditFilter.setEntity(1);
        auditFilter.setEntityType("company");
        auditFilter.setMethod("DESTROY");
        auditFilter.setUser(1);
        return auditFilter;
    }

    @Override
    Filter<Revision> getFilterWithBadParameters() {
        return new AuditFilter();
    }

    @Override
    int parameterSize() {
        return 6;
    }

    @Override
    protected int emptyFilterParameterSize() {
        return 0;
    }
}
