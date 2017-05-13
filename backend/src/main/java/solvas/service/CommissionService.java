package solvas.service;

import solvas.persistence.api.Dao;
import solvas.rest.api.models.ApiCommission;
import solvas.service.mappers.AbstractMapper;
import solvas.service.models.Commission;

/**
 * Created by steve on 13/05/2017.
 */
public class CommissionService extends AbstractService<Commission,ApiCommission> {
    /**
     * Contruct an abstractservice
     *
     * @param modelDao the DAO of the model
     * @param mapper   the mapper between the apimodel and the model
     */
    public CommissionService(Dao<Commission> modelDao, AbstractMapper<Commission, ApiCommission> mapper) {
        super(modelDao, mapper);
    }
}
