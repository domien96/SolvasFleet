package solvas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import solvas.persistence.api.Dao;
import solvas.persistence.api.Filter;
import solvas.rest.api.models.ApiContract;
import solvas.service.mappers.AbstractMapper;
import solvas.service.models.Insurance;

/**
 * Service class of Contracts and contracttypes.
 * Created by domien on 30/03/2017.
 */
public class ContractService extends AbstractService<Insurance,ApiContract> {
    /**
     * Contruct an abstractservice
     *
     * @param modelDao the DAO of the model
     * @param mapper   the mapper between the apimodel and the model
     */
    public ContractService(Dao<Insurance> modelDao, AbstractMapper<Insurance, ApiContract> mapper) {
        super(modelDao, mapper);
    }
}
