package filters;

import solvas.persistence.api.Filter;
import solvas.rest.query.ArchiveFilter;
import solvas.rest.query.RoleFilter;
import solvas.service.models.Role;

public class RoleFilterTest extends AbstractFilterTest<Role>{
    private RoleFilter roleFilter = new RoleFilter();

    @Override
    Filter<Role> getFilterWithCorrectParameters() {

        roleFilter.setUser(1);
        return roleFilter;
    }

    @Override
    Filter<Role> getFilterWithBadParameters() {
        return roleFilter;
    }

    @Override
    int parameterSize() {
        return 2;
    }
}
