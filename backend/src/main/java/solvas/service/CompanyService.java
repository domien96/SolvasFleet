package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.mappers.ContractMapper;
import solvas.service.mappers.FleetMapper;
import solvas.service.models.*;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.CompanyMapper;
import solvas.rest.api.models.ApiCompany;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * CompanyService class
 */
@Service
public class CompanyService extends AbstractService<Company,ApiCompany> {

    @Autowired
    private FunctionService functionService;

    /**
     * Construct a CompanyService
     * @param context the daocontext
     * @param mapper the mapper to map ApiCompany and Company
     */
    @Autowired
    public CompanyService(DaoContext context, CompanyMapper mapper) {
        super(context.getCompanyDao(),context, mapper);
    }




    @Override
    public void archive(int id) throws EntityNotFoundException {
        Company company = context.getCompanyDao().find(id);
        LocalDateTime now = LocalDateTime.now();

        // stop each active contract
        for (Contract contract:context.getContractDao().findByCompany(company)){
            contract.setEndDate(now);
            context.getContractDao().save(contract);
        }

        // Archive each fleet associated with this company
        FleetService fleetService = new FleetService(context, new FleetMapper(context));
        for (Fleet fleet : context.getFleetDao().findByCompany(company)) {
            fleetService.archive(fleet.getId());
        }

        // Archive each function associated with this company
        for (Function function :  context.getFunctionDao().findByCompanyAndArchivedFalse(company)) {
            functionService.archive(function.getId());
        }

        super.archive(id);
    }
}
