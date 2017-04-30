package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.persistence.api.EntityNotFoundException;
import solvas.service.mappers.ContractMapper;
import solvas.service.mappers.FleetMapper;
import solvas.service.models.Company;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.CompanyMapper;
import solvas.rest.api.models.ApiCompany;
import solvas.service.models.CompanyType;
import solvas.service.models.Contract;
import solvas.service.models.Fleet;

/**
 * CompanyService class
 */
@Service
public class CompanyService extends AbstractService<Company,ApiCompany> {

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

        // stop and archive each contract, when company is of type  insurance company
        if (company.getType().equals(CompanyType.INSURANCECOMPANY)){
            ContractService contractService = new ContractService(context,new ContractMapper(context));
            for (Contract contract:context.getContractDao().findByCompany(company)){
                contractService.archive(contract.getId());
            }
        }

        // archive each fleet when the company is a customer
        if (company.getType().equals(CompanyType.CUSTOMER)) {
            FleetService fleetService = new FleetService(context, new FleetMapper(context));
            for (Fleet fleet : context.getFleetDao().findByCompany(company)) {
                fleetService.archive(fleet.getId());
            }
        }

        //TODO when company is leasing company

        super.archive(id);
    }
}
