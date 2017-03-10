package dao;

import matchers.CompanyMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import solvas.models.Company;
import solvas.persistence.HibernateConfig;
import solvas.persistence.company.CompanyDao;

import javax.transaction.Transactional;
import java.util.Iterator;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

/**
 * Integration tests of CompanyDAO
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {HibernateConfig.class,HibernateTestConfig.class},loader = AnnotationConfigContextLoader.class)
@Transactional
public class CompanyDaoTest {

    @Autowired
    private CompanyDao companyDao;
    private Company company;
    private CompanyMatcher matcher = new CompanyMatcher();

    @Before
    public void setUp()
    {
        company=random(Company.class);
        company.setId(0); //new company has id 0
    }

    /**
     * Test: adding a company to the database
     */
    @Test
    public void addCompany()
    {
        companyDao.save(company);
        assertThat(companyDao.findAll(),hasSize(1));
        assertThat(company,is(equalTo(companyDao.find(company.getId()))));
    }

    /**
     * Test: deleting a company on the database
     */
    @Test
    public void destroyCompany()
    {
        companyDao.save(company);
        assertThat(companyDao.findAll(),hasSize(1));
        companyDao.destroy(company);
        assertThat(companyDao.findAll(),hasSize(0));
    }

    /**
     * Test: updating a company on the database
     */
    @Test
    public void updateCompany()
    {
        companyDao.save(company);
        Company update = random(Company.class);
        update.setId(company.getId());
        companyDao.save(update);
        matcher.performAsserts(companyDao.find(company.getId()),update);
    }

    /**
     * Test: finding a company on the database
     */
    @Test
    public void findCompanyById()
    {
        companyDao.save(company);
        //Dao automatically changes the id of the object aswell
        new CompanyMatcher().performAsserts(company,companyDao.find(company.getId()));
    }

    /**
     * Test: receiving all the companies from the database
     */
    @Test
    public void findCompanies()
    {

        assertThat(companyDao.findAll(),hasSize(0));
        companyDao.save(company);
        Company second = random(Company.class);
        second.setId(0);
        companyDao.save(second);
        assertThat(companyDao.findAll(),hasSize(2));
        Iterator<Company> i = companyDao.findAll().iterator();
        matcher.performAsserts(i.next(),company);
        matcher.performAsserts(i.next(),second);
    }

    /**
     * Test: finding certain companies with a name
     */
    public void withName()
    {
        assertThat(companyDao.withName("aa"),hasSize(0));
        company.setName("Fortis");
        Company second = random(Company.class);
        second.setId(0);
        companyDao.save(company);
        companyDao.save(second);
        Iterator<Company> i = companyDao.withName("Fo").iterator();
        matcher.performAsserts(company,i.next());
    }
}
