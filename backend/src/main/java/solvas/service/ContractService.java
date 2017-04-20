package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.InsuranceTypeDao;
import solvas.rest.api.models.ApiContract;
import solvas.service.mappers.ContractMapper;
import solvas.service.models.Contract;
import solvas.service.models.InsuranceType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Service class of Contracts and contracttypes.
 * Created by domien on 30/03/2017.
 */
@Service
public class ContractService extends AbstractService<Contract,ApiContract> {

    //Dao for generating a response for findAllInsuranceTypes
    private InsuranceTypeDao insuranceTypeDao;

    /**
     * Contruct an abstractservice
     *
     * @param context the DAO context
     * @param mapper   the mapper between the apimodel and the model
     */
    @Autowired
    public ContractService(DaoContext context, ContractMapper mapper) {
        super(context.getContractDao(),context, mapper);
        insuranceTypeDao= context.getInsuranceTypeDao();
    }


    /**
     * Finds all types of insurance in the database
     * @return types of insurance
     */
    public Collection<String> findAllInsuranceTypes() {
        return insuranceTypeDao.findAll().stream().map(InsuranceType::getName).collect(Collectors.toSet());

    }

    @Override
    public void archive(int id) throws EntityNotFoundException {
        Contract contract = context.getContractDao().find(id);
        // set endDate of contract
        contract.setEndDate(LocalDateTime.now()); // TODO ask patrick if this is the right way
        modelDao.save(contract);
        super.archive(id);
    }

}
