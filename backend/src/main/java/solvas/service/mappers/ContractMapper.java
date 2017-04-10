package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.utils.SimpleUrlBuilder;
import solvas.rest.api.models.ApiContract;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Contract;
import solvas.service.models.FleetSubscription;

import java.util.Collection;
import java.util.stream.Collectors;

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
        super(daoContext,"endDate","franchise","premium","startDate");
    }

    @Override
    public Contract convertToModel(ApiContract api) throws DependantEntityNotFound, EntityNotFoundException {
        Contract contract = api.getId()==0? new Contract():daoContext.getContractDao().find(api.getId());
        copySharedAttributes(contract, api);
        contract.setInsuranceType(new InsuranceTypeMapper(daoContext).convertToModel(api.getType()));
        contract.setCompany(daoContext.getCompanyDao().find(api.getInsuranceCompany()));

        Collection<FleetSubscription> sub = daoContext.getFleetSubscriptionDao().findByVehicle(
                daoContext.getVehicleDao().find(api.getVehicle())).stream().filter(fleetSubscription -> {
                    return true;/* //TODO remove as this is test
                    return ((fleetSubscription.getStartDate().compareTo(api.getStartDate().toLocalDate()) <=0)
                            && ((fleetSubscription.getEndDate()==null)  || (fleetSubscription.getEndDate().compareTo(api.getEndDate().toLocalDate()) >= 0 )));*/
                }).collect(Collectors.toSet());

        if (sub.size()!=1) {throw new EntityNotFoundException(); }// TODO make beter exception

        //Size of sub always = 1 see previous line, since a collection doesn't have a get method, a loop will do the trick
        sub.forEach(contract::setFleetSubscription);
        return contract;
    }

    @Override
    public ApiContract convertToApiModel(Contract model) {
        ApiContract api = new ApiContract();

        copyAttributes(api, model, "id", "createdAt", "updatedAt");
        copySharedAttributes(api, model);

        api.setVehicle(model.getFleetSubscription().getVehicle().getId());
        api.setType(new InsuranceTypeMapper(daoContext).convertToApiModel(model.getInsuranceType()));
        api.setInsuranceCompany(model.getCompany().getId());

        api.setUrl(ROOTPATH + api.getId());
        return api;

    }

}
