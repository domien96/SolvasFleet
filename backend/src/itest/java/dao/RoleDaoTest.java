package dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import solvas.models.Role;
import solvas.persistence.EntityNotFoundException;
import solvas.persistence.HibernateConfig;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.role.RoleDao;
import solvas.persistence.user.UserDao;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

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

    @Before
    public void setUp()
    {
        role=random(Role.class,"id");
        System.out.println("teeeeeeeeeeeeeeeeeeeeeeeee"+ role.getUpdatedAt());
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
        assertRoles(role,roleDao.find(role.getId()));
    }

    /**
     * Test: delete a role on the database
     */
    @Test(expected = EntityNotFoundException.class)
    public void destroyRole()
    {
        roleDao.destroy(role);
        assertThat(roleDao.findAll(),hasSize(99));
        roleDao.find(role.getId());
    }

    /**
     * Test: update a role on the database
     */
    @Test
    public void updateRole()
    {
        role.setId(10);
        roleDao.save(role);
        assertRoles(role,roleDao.find(role.getId()));
    }

    /**
     * Test: find a specific role
     */
    @Test
    public void findRoleById()
    {
        assertThat(roleDao.find(10),notNullValue());
    }

    /**
     * Test: get all roles from the database
     */
    @Test
    public void findRoles()
    {
        assertThat(roleDao.findAll(),hasSize(100));
    }

    private void assertRoles(Role expected, Role actual)
    {
        assertThat(actual.getId(),is(equalTo(expected.getId())));
        assertThat(actual.getUrl(),is(equalTo(expected.getUrl())));
        assertThat(actual.getFunction(),is(equalTo(expected.getFunction())));
    }
}
