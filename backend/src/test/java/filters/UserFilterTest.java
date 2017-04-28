package filters;

import solvas.rest.query.ArchiveFilter;
import solvas.rest.query.UserFilter;
import solvas.service.models.User;

public class UserFilterTest extends AbstractFilterTest<User> {
    private UserFilter filter = new UserFilter();

    @Override
    ArchiveFilter<User> getFilterWithCorrectParameters() {
        filter.setEmail("a@a.be");
        filter.setFirstName("jan");
        filter.setLastName("janssens");
        return filter;
    }

    @Override
    ArchiveFilter<User> getFilterWithBadParameters() {
        return filter;
    }

    @Override
    int parameterSize() {
        return 4;
    }
}
