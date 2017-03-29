package solvas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.service.models.Company;
import solvas.persistence.api.DaoContext;
import solvas.service.mappers.CompanyMapper;
import solvas.rest.api.models.ApiCompany;

/**
 * CompanyService class
 */
@Service
public class CompanyService extends AbstractService<Company,ApiCompany> {

    /**
     * Construct a CcmpanyService
     * @param context the daocontext
     * @param mapper the mapper to map ApiCompany and Company
     */
    @Autowired
    public CompanyService(DaoContext context, CompanyMapper mapper) {
        super(context.getCompanyDao(), mapper);
    }
}
