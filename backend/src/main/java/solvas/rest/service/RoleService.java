package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.Role;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.mappers.RoleMapper;
import solvas.rest.api.models.ApiRole;

/**
 * RoleService class
 */
@Service
public class RoleService extends AbstractService<Role,ApiRole>{
    @Autowired
    public RoleService(DaoContext context, RoleMapper mapper) {
        super(context.getRoleDao(), mapper);
    }
}
