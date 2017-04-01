package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.Filter;
import solvas.rest.api.models.ApiContract;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.ContractMapper;
import solvas.service.models.Contract;
import solvas.service.models.Insurance;

/**
 * Service class of Contracts and contracttypes.
 * Created by domien on 30/03/2017.
 */
@Service
public class ContractService extends AbstractService<Contract,ApiContract> {
    /**
     * Contruct an abstractservice
     *
     * @param context the DAO context
     * @param mapper   the mapper between the apimodel and the model
     */
    @Autowired
    public ContractService(DaoContext context, ContractMapper mapper) {
        super(context.getContractDao(), mapper);
    }
}
