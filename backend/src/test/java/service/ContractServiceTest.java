package service;

import org.junit.Before;
import org.junit.Ignore;
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

/**
 * Test the contract service.
 */
public class ContractServiceTest extends AbstractServiceTest<Contract, ApiContract> {

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private ContractDao contractDao;

    @Mock
    private ContractMapper contractMapper;

    /**
     * Construct the test.
     */
    public ContractServiceTest() {
        super(Contract.class, ApiContract.class);
    }

    @Before
    @Override
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getContractDao()).thenReturn(contractDao);
    }

    @Override
    protected AbstractService<Contract, ApiContract> getService() {
        return new ContractService(daoContextMock, contractMapper);
    }

    @Override
    protected Dao<Contract> getDaoMock() {
        return contractDao;
    }

    @Override
    protected AbstractMapper<Contract, ApiContract> getMapperMock() {
        return contractMapper;
    }

    // Contracts don't get archived, they get their end dates altered
    @Override
    @Ignore
    public void archive() throws EntityNotFoundException, UnarchivableException {
        when(getDaoMock().find(anyInt())).thenReturn(getTestModel());
        super.archive();
    }
}
