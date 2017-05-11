package filters;

import solvas.rest.query.ArchiveFilter;
import solvas.rest.query.CompanyFilter;
import solvas.service.models.Company;

public class CompanyFilterTest extends AbstractFilterTest<Company> {
    private CompanyFilter filter = new CompanyFilter();

    @Override
    ArchiveFilter<Company> getFilterWithCorrectParameters() {
        filter.setCity("Ghent");
        filter.setCountry("Be");
        filter.setNameContains("AA");
        filter.setPostalCode("9000");
        filter.setType("TestCompany");
        return filter;
    }

    @Override
    ArchiveFilter<Company> getFilterWithBadParameters() {
        return filter;
    }

    @Override
    int parameterSize() {
        return 6;
    }
}
