package service;

import org.junit.Before;
import org.mockito.Mock;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.UserDao;
import solvas.rest.api.models.ApiUser;
import solvas.service.AbstractService;
import solvas.service.UserService;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.UserMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.User;

import static org.mockito.Mockito.when;

public class UserServiceTest extends AbstractServiceTest<User,ApiUser> {

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @Before
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getUserDao()).thenReturn(userDao);
    }

    public UserServiceTest() {
        super(User.class, ApiUser.class);
    }


    @Override
    protected AbstractService<User, ApiUser> getService() {
        return new UserService(daoContextMock, userMapper);
    }

    @Override
    protected Dao getDaoMock() {
        return userDao;
    }

    @Override
    protected AbstractMapper<User, ApiUser> getMapperMock() {
        return userMapper;
    }
}
