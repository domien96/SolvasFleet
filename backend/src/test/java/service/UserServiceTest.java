package service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import solvas.persistence.api.Dao;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.FunctionDao;
import solvas.persistence.api.dao.UserDao;
import solvas.rest.api.models.ApiUser;
import solvas.service.AbstractService;
import solvas.service.UserService;
import solvas.service.exceptions.UnarchivableException;
import solvas.service.mappers.AbstractMapper;
import solvas.service.mappers.UserMapper;
import solvas.service.mappers.exceptions.DependantEntityNotFound;
import solvas.service.models.User;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest( SecurityContextHolder.class )
public class UserServiceTest extends AbstractServiceTest<User,ApiUser> {

    @Mock
    private DaoContext daoContextMock;
    @Mock
    private UserDao userDao;
    @Mock
    private FunctionDao functionDao;

    @Mock
    private UserMapper userMapper;

    @Before
    @Override
    public void setUp() throws DependantEntityNotFound, EntityNotFoundException {
        super.setUp();
        when(daoContextMock.getUserDao()).thenReturn(userDao);
        when(daoContextMock.getFunctionDao()).thenReturn(functionDao);
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

    @Override
    @Test
    public void archive() throws EntityNotFoundException, UnarchivableException {

        PowerMockito.mockStatic(SecurityContextHolder.class);

        SecurityContext securityContext = new SecurityContextImpl();
        securityContext.setAuthentication( new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        });


        PowerMockito.when(SecurityContextHolder.getContext()).thenReturn(securityContext);

        when(userDao.findByEmail(any())).thenReturn(new User());
        getService().archive(2);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(getDaoMock()).archive(captor.capture());
        assertThat(captor.getValue(),is(2));
    }
}
