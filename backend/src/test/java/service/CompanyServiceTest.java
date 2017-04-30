package service;

import org.junit.Before;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;
import solvas.rest.api.models.ApiCompany;
import solvas.service.AbstractService;
import solvas.service.CompanyService;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.CompanyMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Company;

import static org.mockito.Mockito.when;

public class CompanyServiceTest extends AbstractServiceTest<Company,ApiCompany> {

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private CompanyDao companyDao;

    @Mock
    private CompanyMapper companyMapper;

    @Before
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getCompanyDao()).thenReturn(companyDao);
    }

    public CompanyServiceTest() {
        super(Company.class, ApiCompany.class);
    }


    @Override
    protected AbstractService<Company, ApiCompany> getService() {
        return new CompanyService(daoContextMock,companyMapper);
    }

    @Override
    protected Dao getDaoMock() {
        return companyDao;
    }

    @Override
    protected AbstractMapper<Company, ApiCompany> getMapperMock() {
        return companyMapper;
    }
}
