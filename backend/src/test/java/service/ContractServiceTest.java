package service;

import org.junit.Before;
import org.mockito.Mock;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.ContractDao;
import solvas.rest.api.models.ApiContract;
import solvas.service.AbstractService;
import solvas.service.ContractService;
import solvas.service.exceptions.UnarchivableException;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.ContractMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Contract;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class ContractServiceTest extends AbstractServiceTest<Contract,ApiContract>{

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private ContractDao contractDao;

    @Mock
    private ContractMapper contractMapper;

    @Before
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getContractDao()).thenReturn(contractDao);
    }

    public ContractServiceTest() {
        super(Contract.class, ApiContract.class);
    }


    @Override
    protected AbstractService<Contract, ApiContract> getService() {
        return new ContractService(daoContextMock, contractMapper);
    }

    @Override
    protected Dao getDaoMock() {
        return contractDao;
    }

    @Override
    protected AbstractMapper<Contract, ApiContract> getMapperMock() {
        return contractMapper;
    }

    @Override
    public void archive() throws EntityNotFoundException, UnarchivableException {
        when(getDaoMock().find(anyInt())).thenReturn(getTestModel());
        super.archive();
    }
}
