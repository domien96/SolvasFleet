package mappers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import solvas.models.User;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.UserDao;
import solvas.rest.api.mappers.UserMapper;
import solvas.rest.api.models.ApiUser;

import javax.persistence.Inheritance;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Tests to check correct mapping of a User
 */
@Ignore
public class UserMapperTest {
    @Mock
    private DaoContext daoContext;

    @Mock
    private UserDao userDaoMock;

    private UserMapper mapper;

    /**
     * Setting up the tests of UserMapper
     */
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        when(daoContext.getUserDao()).thenReturn(userDaoMock);
        mapper=new UserMapper(daoContext);
    }

    /**
     * Test the conversion User->ApiUser
     */
    @Test
    public void convertToApiUser()
    {
        User user = random(User.class);
        ApiUser converted = mapper.convertToApiModel(user);
        assertThat(converted.getId(),is(user.getId()));
        assertThat(converted.getUrl(),is("/users/"+user.getId()));
        assertThat(converted.getPassword(),is(user.getPassword()));
        assertThat(converted.getEmail(),is(user.getEmail()));
        assertThat(converted.getFirstName(),is(user.getFirstName()));
        assertThat(converted.getLastName(),is(user.getLastName()));
        assertThat(converted.getUpdatedAt(),is(user.getUpdatedAt()));
        assertThat(converted.getCreatedAt(),is(user.getCreatedAt()));
    }

    /**
     * Test the conversion ApiUser->User
     */
    @Test
    public void convertToUser() throws EntityNotFoundException {
        ApiUser user = random(ApiUser.class);
        user.setId(0);
        User converted = mapper.convertToModel(user);
        assertThat(converted.getLastName(), is(user.getLastName()));
        assertThat(converted.getFirstName(), is(user.getFirstName()));
        assertThat(converted.getPassword(), is(user.getPassword()));
        assertThat(converted.getEmail(), is(user.getEmail()));
    }
}
