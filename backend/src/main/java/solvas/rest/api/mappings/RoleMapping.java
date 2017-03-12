package solvas.rest.api.mappings;

import solvas.models.Role;
import solvas.rest.api.models.ApiRole;

/**
 * Created by steve on 11/03/2017.
 */
public class RoleMapping extends Mapping<Role,ApiRole> {
    @Override
    public Role convertToModel(ApiRole api) {
        Role role = new Role();
        role.setId(api.getId());






        return null;
    }

    @Override
    public ApiRole convertToApiModel(Role role) {
        ApiRole apiRole = new ApiRole();
        return null;
    }
}
