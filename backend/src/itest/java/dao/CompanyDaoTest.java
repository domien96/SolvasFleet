package dao;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import solvas.models.Company;
import solvas.persistence.api.EntityNotFoundException;
import solvas.persistence.api.dao.CompanyDao;
import solvas.persistence.hibernate.HibernateConfig;


import javax.transaction.Transactional;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
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

    @Before
    public void setUp()
    {
        company=random(Company.class,"id");
    }

    /**
     * Test: adding a company to the database
     */
    @Test
    public void addCompany()
    {
        companyDao.create(company);
        assertThat(companyDao.findAll(),hasSize(101));
        assertCompanies(company,companyDao.find(company.getId()));
    }

    /**
     * Test: deleting a company on the database
     */

    @Test(expected = EntityNotFoundException.class)
    public void destroyCompany()
    {
        companyDao.save(company);
        assertThat(companyDao.findAll(),hasSize(101));
        companyDao.destroy(company);
        assertThat(companyDao.findAll(),hasSize(100));
        companyDao.find(company.getId());
    }

    /**
     * Test: updating a company on the database
     */
    @Test
    public void updateCompany()
    {
        Company old = companyDao.find(30);
        company.setId(30);
        company.setRepresentatives(old.getRepresentatives());
        companyDao.update(company);
        assertCompanies(companyDao.find(30),company);
    }

    /**
     * Test: finding a company on the database
     */
    @Test
    public void findCompanyById()
    {
        companyDao.create(company);
        //Dao automatically changes the id of the object aswell
        assertCompanies(company,companyDao.find(company.getId()));
    }

    /**
     * Test: receiving all the companies from the database
     */
    @Test
    public void findCompanies()
    {

        assertThat(companyDao.findAll(),hasSize(100));
    }

    /**
     * Test: finding certain companies with a name
     */
    public void withName()
    {
        assertThat(companyDao.withName("Luctus Inc"),hasSize(1));
    }

    private void assertCompanies(Company actual,Company expected) {
        assertThat(actual.getId(),is(IsEqual.equalTo(expected.getId())));
        assertThat(actual.getName(),is(IsEqual.equalTo(expected.getName())));
        assertThat(actual.getPhoneNumber(),is(IsEqual.equalTo(expected.getPhoneNumber())));
        assertThat(actual.getAddressStreet(),is(IsEqual.equalTo(expected.getAddressStreet())));
        assertThat(actual.getAddressPostalCode(),is(IsEqual.equalTo(expected.getAddressPostalCode())));
        assertThat(actual.getAddressCity(),is(IsEqual.equalTo(expected.getAddressCity())));
        assertThat(actual.getAddressCountry(),is(IsEqual.equalTo(expected.getAddressCountry())));
        assertThat(actual.getAddressHouseNumber(),is(IsEqual.equalTo(expected.getAddressHouseNumber())));
        assertThat(actual.getVatNumber(),is(IsEqual.equalTo(expected.getVatNumber())));
    }
}
