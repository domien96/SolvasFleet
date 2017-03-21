package dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.User;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.UserDao;
import solvas.persistence.hibernate.HibernateConfig;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static solvas.rest.utils.IteratorUtils.toList;

/**
 * Integration tests of UserDao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HibernateConfig.class,HibernateTestConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    private User user;

    @Before
    public void setUp() {
        user = random(User.class, "id");
    }

    /**
     * Test: adding a user to the database
     */
    @Test
    public void addUser()
    {
        userDao.save(user);
        assertThat(toList(userDao.findAll()),hasSize(101));
        assertUsers(user,userDao.find(user.getId()));
    }

    /**
     * Test: deleting a user from the database
     */
    @Ignore //Not sure yet whether we want to have this option. Maybe we should archive users instead
    @Test(expected = EntityNotFoundException.class)
    public void destroyUser()
    {
        User u=userDao.find(30); //anders exception
        userDao.destroy(u);
        assertThat(toList(userDao.findAll()),hasSize(99));
        userDao.find(30);
    }

    /**
     * Test: updating a user on the database
     */
    @Test
    public void updateUser()
    {
        User old = userDao.find(30); //exists
        user.setId(30);
        user.setCompanies(old.getCompanies());
        userDao.save(user);
        assertUsers(user,userDao.find(30));

    }

    /**
     * Test: finding a specific user on the database
     */
    @Test
    public void findUserById()
    {
        userDao.save(user);
        assertUsers(user,userDao.find(user.getId()));
    }

    /**
     * Test: getting all users from the database
     */
    @Test
    public void findUsers()
    {
        assertThat(toList(userDao.findAll()),hasSize(100));
    }

    private void assertUsers(User expected, User actual)
    {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getFirstName(),is(equalTo(expected.getFirstName())));
        assertThat(actual.getLastName(),is(equalTo(expected.getLastName())));
        assertThat(actual.getEmail(),is(equalTo(expected.getEmail())));
        assertThat(actual.getPassword(),is(equalTo(expected.getPassword())));
    }
}
