package dao;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import solvas.models.Company;
import solvas.persistence.HibernateConfig;
import solvas.persistence.company.CompanyDao;
import solvas.persistence.company.HibernateCompanyDao;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = HibernateConfig.class,loader = AnnotationConfigContextLoader.class)
public class CompanyDaoTest {


    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private DataSource dataSource;

    @Test
    public void addCompany()
    {
        Company company = new Company("test","oo","99","OOO","aa");
        company.setId(0);
        companyDao.save(company);
    }

    @Test
    public void destroyCompany()
    {

    }

    @Test
    public void updateCompany()
    {

    }

    @Test
    public void findCompanyById()
    {

    }

    @Test
    public void findCompanies()
    {

    }
}
