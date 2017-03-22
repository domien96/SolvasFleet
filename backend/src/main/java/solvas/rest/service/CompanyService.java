package solvas.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solvas.models.Company;
import solvas.persistence.api.Dao;
import solvas.persistence.api.dao.CompanyDao;
import solvas.rest.api.mappers.AbstractMapper;
import solvas.rest.api.mappers.CompanyMapper;
import solvas.rest.api.models.ApiCompany;

/**
 * CompanyService class
 */
@Service
public class CompanyService extends AbstractService<Company,ApiCompany> {

    @Autowired
    public CompanyService(CompanyDao modelDao, CompanyMapper mapper) {
        super(modelDao, mapper);
    }
}
