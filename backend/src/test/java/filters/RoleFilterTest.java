package filters;

import solvas.rest.query.ArchiveFilter;
import solvas.rest.query.RoleFilter;
import solvas.service.models.Role;

public class RoleFilterTest extends AbstractFilterTest<Role>{
    private RoleFilter roleFilter = new RoleFilter();

    @Override
    ArchiveFilter<Role> getFilterWithCorrectParameters() {
        roleFilter.setCompany(10);
        roleFilter.setUser(1);
        return roleFilter;
    }

    @Override
    ArchiveFilter<Role> getFilterWithBadParameters() {
        return roleFilter;
    }

    @Override
    int parameterSize() {
        return 3;
    }
}
