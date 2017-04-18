package filters;

import solvas.rest.query.ArchiveFilter;
import solvas.rest.query.ContractFilter;
import solvas.service.models.Contract;

public class ContractFilterTest extends AbstractFilterTest<Contract> {
    private ContractFilter filter = new ContractFilter();

    @Override
    ArchiveFilter<Contract> getFilterWithCorrectParameters() {
        filter.setCompany(1);
        filter.setFleet(1);
        filter.setVehicle(1);
        return filter;
    }

    @Override
    ArchiveFilter<Contract> getFilterWithBadParameters() {
        return filter;
    }

    @Override
    int parameterSize() {
        return 4;
    }
}
