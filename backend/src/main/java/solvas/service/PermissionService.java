package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.Dao;
import solvas.rest.api.models.ApiPermission;
import solvas.service.mappers.AbstractMapper;
import solvas.service.models.Permission;

/**
 * Service layer for permissions
 */
@Service
public class PermissionService extends AbstractService<Permission, ApiPermission> {
    /**
     * Contruct an abstractservice
     *
     * @param modelDao the DAO of the model
     * @param mapper   the mapper between the apimodel and the model
     */
    @Autowired
    public PermissionService(Dao<Permission> modelDao, AbstractMapper<Permission, ApiPermission> mapper) {
        super(modelDao, mapper);
    }
}
