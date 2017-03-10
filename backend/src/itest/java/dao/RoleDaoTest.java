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

import java.util.Iterator;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Integration tests of RoleDao
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HibernateConfig.class,HibernateTestConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class RoleDaoTest {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private CompanyDao companyDao;

    private Role role;
    private RoleMatcher matcher = new RoleMatcher();

    @Before
    public void setUp()
    {
        role=random(Role.class);
        role.getCompany().setId(0);
        //Company has to be in the database before adding the role
        role.setCompany(companyDao.save(role.getCompany()));
        role.setId(0); //new role has id 0
    }

    /**
     * Test: adding a role to the database
     */
    @Test
    public void addRole()
    {
        roleDao.save(role);
        assertThat(roleDao.findAll(),hasSize(1));
        matcher.performAsserts(role,roleDao.find(role.getId()));
    }

    /**
     * Test: delete a role on the database
     */
    @Test
    public void destroyRole()
    {
        roleDao.save(role);
        assertThat(roleDao.findAll(),hasSize(1));
        roleDao.destroy(role);
        assertThat(roleDao.findAll(),hasSize(0));
    }

    /**
     * Test: update a role on the database
     */
    @Test
    public void updateRole()
    {
        roleDao.save(role);
        role.setUrl("newurl");
        Role update=random(Role.class);
        update.setId(role.getId());
        roleDao.save(update);
        matcher.performAsserts(update,roleDao.find(role.getId()));
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
        Role second = random(Role.class);
        second.setId(0);
        roleDao.save(role);
        roleDao.save(second);
        assertThat(roleDao.findAll(),hasSize(2));
        Iterator<Role> i = roleDao.findAll().iterator();
        matcher.performAsserts(role,i.next());
        matcher.performAsserts(second,i.next());
    }
}
