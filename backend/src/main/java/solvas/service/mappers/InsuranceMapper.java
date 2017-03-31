package solvas.service.mappers;

import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.rest.api.models.ApiContract;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Company;
import solvas.service.models.Insurance;
import solvas.service.models.InsuranceCoverage;

/**
 * Created by steve on 31/03/2017.
 */
public class InsuranceMapper extends AbstractMapper<InsuranceCoverage,ApiContract> {
    public InsuranceMapper(DaoContext daoContext) {
        super(daoContext);
    }

    @Override
    public InsuranceCoverage convertToModel(ApiContract api) throws DependantEntityNotFound, EntityNotFoundException {
        /*InsuranceCoverage company = apiCompany.getId()==0? new Company():daoContext.getCompanyDao().find(apiCompany.getId());*/


        return null;
    }

    @Override
    public ApiContract convertToApiModel(InsuranceCoverage model) {
        return null;
    }
}
