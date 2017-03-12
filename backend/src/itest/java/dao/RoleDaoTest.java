package dao;

import matchers.RoleMatcher;
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
import solvas.models.Role;
import solvas.persistence.HibernateConfig;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.user.UserDao;

import java.util.Iterator;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Integration tests of RoleDao
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HibernateConfig.class,HibernateTestConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private UserDao userDao;

    private Role role;
    private RoleMatcher matcher = new RoleMatcher();

    @Before
    public void setUp()
    {
        role=random(Role.class,"id");
        role.getCompany().setId(0);
        companyDao.save(role.getCompany());
        role.getUser().setId(0);
        userDao.save(role.getUser());
    }

    /**
     * Test: adding a role to the database
     */
    @Test
    public void addRole()
    {
        roleDao.save(role);
        assertThat(roleDao.findAll(),hasSize(101));
        matcher.performAsserts(role,roleDao.find(role.getId()));
    }

    /**
     * Test: delete a role on the database
     */
    @Test
    public void destroyRole()
    {
        roleDao.save(role);
        assertThat(roleDao.findAll(),hasSize(101));
        roleDao.destroy(role);
        assertThat(roleDao.findAll(),hasSize(100));
    }

    /**
     * Test: update a role on the database
     */
    @Test
    public void updateRole()
    {
        role.setId(10);
        roleDao.save(role);
        matcher.performAsserts(role,roleDao.find(role.getId()));
    }

    /**
     * Test: find a specific role
     */
    @Test
    public void findRoleById()
    {
        roleDao.save(role);
        matcher.performAsserts(role,roleDao.find(role.getId()));
    }

    /**
     * Test: get all roles from the database
     */
    @Test
    public void findRoles()
    {
        assertThat(roleDao.findAll(),hasSize(100));
    }
}
