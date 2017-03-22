package solvas.rest.service;

import solvas.models.Company;
import solvas.persistence.api.Dao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.models.ApiCompany;

/**
 * CompanyService class
 */
public class CompanyService extends AbstractService<Company,ApiCompany> {

    public CompanyService(Dao<Company> modelDao, AbstractMapper<Company, ApiCompany> mapper) {
        super(modelDao, mapper);
    }
}
