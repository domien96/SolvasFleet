package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.Role;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.RoleDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.RoleMapper;
import solvas.rest.api.models.ApiRole;

/**
 * RoleService class
 */
@Service
public class RoleService extends AbstractService<Role,ApiRole>{
    @Autowired
    public RoleService(RoleDao modelDao, RoleMapper mapper) {
        super(modelDao, mapper);
    }
}
