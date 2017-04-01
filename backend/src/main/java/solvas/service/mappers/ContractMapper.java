package solvas.service.mappers;

import org.springframework.stereotype.Component;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiContract;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Contract;

/**
 * Mapper between Contract and ApiContract
 */
@Component
public class ContractMapper extends AbstractMapper<Contract,ApiContract> {

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
        /*Contract company = apiCompany.getId()==0? new Company():daoContext.getCompanyDao().find(apiCompany.getId());*/


        return null;
    }

    @Override
    public ApiContract convertToApiModel(Contract model) {
        return null;
    }
}
