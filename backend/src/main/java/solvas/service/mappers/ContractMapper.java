package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiContract;
import solvas.rest.utils.SimpleUrlBuilder;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Contract;
import solvas.service.models.FleetSubscription;
import solvas.service.models.Vehicle;

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

        Vehicle vehicle = daoContext.getVehicleDao().find(api.getVehicle());

        FleetSubscription subscription = daoContext.getFleetSubscriptionDao()
                .activeForVehicleBetween(vehicle, api.getStartDate().toLocalDate(), api.getEndDate().toLocalDate())
                .orElseThrow(() -> new EntityNotFoundException("Contract: Matched 2 fleet subscriptions"));

        contract.setFleetSubscription(subscription);

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

        api.setUrl(SimpleUrlBuilder.buildUrlFromBase(ROOTPATH + "{id}", api.getId()));
        return api;

    }

}
