package filters;

import solvas.rest.query.ArchiveFilter;
import solvas.rest.query.FleetFilter;
import solvas.service.models.Fleet;

public class FleetFilterTest extends AbstractFilterTest<Fleet>{
    private FleetFilter fleetFilter=new FleetFilter();
    @Override
    ArchiveFilter<Fleet> getFilterWithCorrectParameters() {
        fleetFilter.setCompany(10);
        return fleetFilter;

    }

    @Override
    ArchiveFilter<Fleet> getFilterWithBadParameters() {
        return fleetFilter;
    }

    @Override
    int parameterSize() {
        return 2;
    }
}
