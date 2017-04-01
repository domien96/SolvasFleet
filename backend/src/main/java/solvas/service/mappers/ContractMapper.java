package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiContract;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Company;
import solvas.service.models.Contract;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Insurance;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Mapper between Contract & Insurance and ApiContract
 */
@Component
public class ContractMapper extends AbstractMapper<Contract,ApiContract> {

    private static final String ROOTPATH = "/contracts/";

    /**
     * Create a mapper between Contract and ApiContract
     *
     * @param daoContext The context for Dao's
     */
    public ContractMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public Contract convertToModel(ApiContract api) throws DependantEntityNotFound, EntityNotFoundException {
        Contract contract = api.getId()==0? new Contract():daoContext.getContractDao().find(api.getId());
        contract.setBeginDate(LocalDateTime.of(api.getStartDate(), null));
        contract.setEndDate(LocalDateTime.of(api.getEndDate(), null));
        contract.setInsurance(convertToInsuranceModel(api));
        Collection<FleetSubscription> sub = daoContext.getFleetSubscriptionDao().findByVehicle(
                daoContext.getVehicleDao().find(api.getVehicle()));
        //contract.setFleetSubscription(sub);
        // todo hoe weet ik nu welke subscription de juiste is..

        return contract;
    }

    @Override
    public ApiContract convertToApiModel(Contract model) {
        return null;
    }

    private Insurance convertToInsuranceModel(ApiContract api) throws EntityNotFoundException {
        Insurance x = new Insurance();
        x.setRiskPremium(api.getPremium());
        x.setApplicableExemption(0); //TODO not defined in api
        x.setCompany(daoContext.getCompanyDao().find(api.getInsuranceCompany()));
        x.setInsuranceType(daoContext.getInsuranceTypeDao().findByName(api.getType()));
        return x;
    }
}
