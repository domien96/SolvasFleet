package mappers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import solvas.models.User;
import solvas.persistence.api.DaoContext;
import solvas.persistence.api.dao.UserDao;
import solvas.rest.api.mappers.UserAbstractMapper;
import solvas.rest.api.models.ApiUser;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

/**
 * Tests to check correct mapping of a User
 */
public class UserMapperTest {
    @Mock
    private DaoContext daoContext;

    @Mock
    private UserDao userDaoMock;

    private UserAbstractMapper mapper;

    /**
     * Setting up the tests of UserMapper
     */
    @Before
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        when(daoContext.getUserDao()).thenReturn(userDaoMock);
        mapper=new UserAbstractMapper(daoContext);
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
    public void convertToUser() {
        ApiUser user = random(ApiUser.class);
        User converted = mapper.convertToModel(user);
        assertThat(converted.getLastName(), is(user.getLastName()));
        assertThat(converted.getFirstName(), is(user.getFirstName()));
        assertThat(converted.getPassword(), is(user.getPassword()));
        assertThat(converted.getEmail(), is(user.getEmail()));
    }
}
