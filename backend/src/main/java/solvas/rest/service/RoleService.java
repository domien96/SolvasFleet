package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.Role;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.RoleDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.DependantEntityNotFound;
import solvas.rest.api.mappers.RoleMapper;
import solvas.rest.api.models.ApiRole;

/**
 * RoleService class
 */
@Service
public class RoleService extends AbstractService<Role,ApiRole>{
    @Autowired
    private DaoContext context;

    @Autowired
    public RoleService(DaoContext context, RoleMapper mapper) {
        super(context.getRoleDao(), mapper);
    }

    @Override
    public ApiRole update(int id,ApiRole input)
    {
        input.setId(id);
        Role role=mapper.convertToModel(input,modelDao.find(id));
        try {
            role.setCompany(context.getCompanyDao().find(input.getCompany()));
            role.setUser(context.getUserDao().find(input.getUser()));
        } catch (EntityNotFoundException e) {
            throw new DependantEntityNotFound("Could not find company/user.", e);
        }
        return mapper.convertToApiModel(modelDao.update(role));
    }
}
