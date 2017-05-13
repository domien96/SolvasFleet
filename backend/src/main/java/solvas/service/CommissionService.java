package solvas.service;

import org.springframework.stereotype.Service;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.CommissionDao;
import solvas.rest.api.models.ApiCommission;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.CommissionMapper;
import solvas.service.models.Commission;

/**
 * Created by steve on 13/05/2017.
 */
@Service
public class CommissionService extends AbstractService<Commission,ApiCommission> {
    /**
     * Contruct an abstractservice
     *
     * @param modelDao the DAO of the model
     * @param mapper   the mapper between the apimodel and the model
     */
    public CommissionService(CommissionDao modelDao, CommissionMapper mapper) {
        super(modelDao, mapper);
    }
}
