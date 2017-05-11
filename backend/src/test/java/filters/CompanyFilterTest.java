package filters;

import solvas.rest.query.ArchiveFilter;
import solvas.rest.query.CompanyFilter;
import solvas.service.models.Company;

/**
 * Test the {@link CompanyFilter}. This will test that every constraint results in a constraint. This does not
 * actually test if the constraints are correct, that is something for an integration test.
 */
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