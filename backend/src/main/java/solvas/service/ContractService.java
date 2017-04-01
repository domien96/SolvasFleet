package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.rest.api.models.ApiContract;
import solvas.service.mappers.ContractMapper;
import solvas.service.models.Contract;

import java.util.Collection;
import java.util.stream.Collectors;

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

    public Collection<String> findAllInsuranceTypes() {
        return modelDao.findAll().stream().map(Contract::getInsuranceType).collect(Collectors.toSet());
    }
}
