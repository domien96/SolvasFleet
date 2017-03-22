package solvas.rest.service;

import solvas.models.Role;
import solvas.persistence.api.Dao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.models.ApiRole;

/**
 * RoleService class
 */
public class RoleService extends AbstractService<Role,ApiRole>{
    public RoleService(Dao<Role> modelDao, AbstractMapper<Role, ApiRole> mapper) {
        super(modelDao, mapper);
    }
}
