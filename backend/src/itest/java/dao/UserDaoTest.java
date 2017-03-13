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
import solvas.persistence.EntityNotFoundException;
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
        assertThat(userDao.findAll(),hasSize(101));
        matcher.performAsserts(user,userDao.find(user.getId()));
    }

    /**
     * Test: deleting a user from the database
     */
    @Ignore //Deleting a user requires deleting the role first?
    @Test(expected = EntityNotFoundException.class)
    public void destroyUser()
    {
        User u=userDao.find(30); //anders exception
        userDao.destroy(u);
        assertThat(userDao.findAll(),hasSize(99));
        userDao.find(user.getId());
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
        assertThat(userDao.findAll(),hasSize(100));
    }
}
