package service;

import org.junit.Before;
import org.mockito.Mock;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FunctionDao;
import solvas.persistence.api.dao.RoleDao;
import solvas.rest.api.models.ApiRole;
import solvas.service.AbstractService;
import solvas.service.RoleService;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.RoleMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.Role;

import static org.mockito.Mockito.when;

/**
 * Test the role service.
 */
public class RoleServiceTest extends AbstractServiceTest<Role, ApiRole> {

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private RoleDao roleDao;

    @Mock
    private FunctionDao functionDao;

    @Mock
    private RoleMapper roleMapper;

    /**
     * Construct the test.
     */
    public RoleServiceTest() {
        super(Role.class, ApiRole.class);
    }

    @Before
    @Override
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getRoleDao()).thenReturn(roleDao);
        when(daoContextMock.getFunctionDao()).thenReturn(functionDao);
    }

    @Override
    protected AbstractService<Role, ApiRole> getService() {
        return new RoleService(daoContextMock, roleMapper);
    }

    @Override
    protected Dao<Role> getDaoMock() {
        return roleDao;
    }

    @Override
    protected AbstractMapper<Role, ApiRole> getMapperMock() {
        return roleMapper;
    }
}
