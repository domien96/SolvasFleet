package dao;

import matchers.UserMatcher;
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
import solvas.persistence.HibernateConfig;
import solvas.persistence.user.UserDao;

import java.util.Iterator;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

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
    private UserMatcher matcher = new UserMatcher();

    @Before
    public void setUp()
    {
        user=random(User.class);
        user.setId(0); //new company has id 0
    }

    /**
     * Test: adding a user to the database
     */
    @Test
    public void addUser()
    {
        userDao.save(user);
        assertThat(userDao.findAll(),hasSize(1));
        matcher.performAsserts(user,userDao.find(user.getId()));
    }

    /**
     * Test: deleting a user from the database
     */
    @Test
    public void destroyUser()
    {
        userDao.save(user);
        assertThat(userDao.findAll(),hasSize(1));
        userDao.destroy(user);
        assertThat(userDao.findAll(),hasSize(0));
    }

    /**
     * Test: updating a user on the database
     */
    @Test
    public void updateUser()
    {
        userDao.save(user);
        User updated = random(User.class);
        updated.setId(user.getId());
        userDao.save(updated);
        matcher.performAsserts(updated,userDao.find(user.getId()));
    }

    /**
     * Test: finding a specific user on the database
     */
    @Test
    public void findUserById()
    {
        userDao.save(user);
        matcher.performAsserts(user,userDao.find(user.getId()));
    }

    /**
     * Test: getting all users from the database
     */
    @Test
    public void findUsers()
    {
        userDao.save(user);
        User second = random(User.class);
        second.setId(0);
        userDao.save(second);
        assertThat(userDao.findAll(),hasSize(2));
        Iterator<User> i = userDao.findAll().iterator();
        matcher.performAsserts(user,i.next());
        matcher.performAsserts(second,i.next());
    }
}
